package eu.ensup.my_resto.service;

import eu.ensup.my_resto.domain.Item;
import eu.ensup.my_resto.domain.Order;
import eu.ensup.my_resto.domain.OrderItem;
import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.model.ItemDTO;
import eu.ensup.my_resto.model.OrderDTO;
import eu.ensup.my_resto.model.OrderItemsDTO;
import eu.ensup.my_resto.model.Status;
import eu.ensup.my_resto.repos.ItemRepository;
import eu.ensup.my_resto.repos.OrderItemRepository;
import eu.ensup.my_resto.repos.OrderRepository;
import eu.ensup.my_resto.repos.UserRepository;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.hibernate.boot.model.source.spi.SingularAttributeSourceToOne;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Transactional
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(final OrderRepository orderRepository, final UserRepository userRepository,
                        final ItemRepository itemRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .collect(Collectors.toList());
    }

    public List<OrderDTO> findAllByUser(User user) {
        return orderRepository.findAllByUser(user)
                .stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .collect(Collectors.toList());
    }

    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        order.setStatus(String.valueOf(Status.EN_ATTENTE));
        Long id = orderRepository.save(order).getId();
        order.setId(id);

        final List<Item> items = itemRepository.findAllById(
                orderDTO.getItems().stream()
                        .map(orderItemsDTO -> orderItemsDTO.getItem().getId())
                        .collect(Collectors.toList()));

        // Update order and item registry
        final Set<OrderItem> orderItems = items.stream().map(item -> OrderItem
                        .builder()
                        .order(order)
                        .item(item)
                        .quantity(orderDTO.getItems().stream().filter(item1 -> Objects.equals(item1.getItem().getId(), item.getId())).map(OrderItemsDTO::getQuantity).collect(toSingleton()))
                        .build())
                .collect(Collectors.toSet());
        orderItemRepository.saveAll(orderItems);

        // Update storage
        orderItems.forEach(orderItem -> {
           Item item = itemRepository.findById(orderItem.getItem().getId()).get();
           item.setQuantity(item.getQuantity() - orderItem.getQuantity());
           itemRepository.save(item);
        });

        return id;
    }

    public void update(final Long id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Boolean reqOrderIsCancelled = orderDTO.getStatus().equals(Status.ANNULEE.toString());
        Boolean repoOrderIsCancelled = order.getStatus().equals(Status.ANNULEE.toString());

        mapToEntity(orderDTO, order);

        // update storage
        if(reqOrderIsCancelled && !repoOrderIsCancelled){ // The status has just changed
            orderDTO.getItems().forEach(orderItem -> {
                Item itemRepo = itemRepository.findById(orderItem.getItem().getId()).get();
                itemRepo.setQuantity(itemRepo.getQuantity() + orderItem.getQuantity());
                itemRepository.save(itemRepo);
            });

            order.setStatus(Status.TERMINEE.toString());
        }
        orderRepository.save(order);
    }

    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {

        orderDTO.setId(order.getId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setAddress(order.getAddress());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setUser(order.getUser() == null ? null : order.getUser().getId());
        List<OrderItem> items = orderItemRepository.findAll().stream().filter(orderItem -> Objects.equals(orderItem.getOrder().getId(), order.getId())).collect(Collectors.toList());
        List<OrderItemsDTO> orderItemDTOS = items.stream().map(orderItem -> OrderItemsDTO.builder().item(mapToItemDTO(orderItem.getItem())).quantity(orderItem.getQuantity()).build()).collect(Collectors.toList());
        orderDTO.setItems(orderItemDTOS);
        return orderDTO;
    }

    private ItemDTO mapToItemDTO(final Item item){
        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .quantity(item.getQuantity())
                .description(item.getDescription())
                .price(item.getPrice())
                .category(item.getCategory())
                .image(item.getImage()).build();
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setStatus(orderDTO.getStatus());
        order.setAddress(orderDTO.getAddress());
        order.setPrice(orderDTO.getPrice());
        if (orderDTO.getUser() != null && (order.getUser() == null || !order.getUser().getId().equals(orderDTO.getUser()))) {
            final User user = userRepository.findById(orderDTO.getUser())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
            order.setUser(user);
        }
        order.setOrderItems(null);
        return order;
    }

    public static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
            Collectors.toList(),
            list -> {
                if (list.size() != 1) {
                    throw new IllegalStateException();
                }
                return list.get(0);
            }
        );
    }

}