package eu.ensup.my_resto.service;

import eu.ensup.my_resto.domain.Item;
import eu.ensup.my_resto.domain.Order;
import eu.ensup.my_resto.domain.OrderItem;
import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.model.*;
import eu.ensup.my_resto.repos.ItemRepository;
import eu.ensup.my_resto.repos.OrderItemRepository;
import eu.ensup.my_resto.repos.OrderRepository;
import eu.ensup.my_resto.repos.Projections.MapProjection;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * The type Order service.
 */
@Transactional
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    /**
     * Instantiates a new Order service.
     *
     * @param orderRepository     the order repository
     * @param itemRepository      the item repository
     * @param orderItemRepository the order item repository
     */
    public OrderService(final OrderRepository orderRepository,
                        final ItemRepository itemRepository, final OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .collect(Collectors.toList());
    }

    /**
     * Find all by user list.
     *
     * @param user the user
     * @return the list
     */
    public List<OrderDTO> findAllByUser(User user) {
        return orderRepository.findAllByUser(user)
                .stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .collect(Collectors.toList());
    }

    /**
     * Get order dto.
     *
     * @param id the id
     * @return the order dto
     */
    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * Create long.
     *
     * @param orderDTO the order dto
     * @return the long
     */
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

           Optional<Item> item = itemRepository.findById(orderItem.getItem().getId());
           if(item.isPresent()) {
               item.get().setQuantity(item.get().getQuantity() - orderItem.getQuantity());
               itemRepository.save(item.get());
           } else {
               throw new ResponseStatusException(HttpStatus.NOT_FOUND);
           }
        });

        return id;
    }

    /**
     * Update.
     *
     * @param orderDTO the order dto
     */
    public void update(final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean reqOrderIsCancelled = orderDTO.getStatus().equals(Status.ANNULEE.toString());
        boolean repoOrderIsCancelled = order.getStatus().equals(Status.ANNULEE.toString());

        mapToEntity(orderDTO, order);

        // update storage
        if(reqOrderIsCancelled && !repoOrderIsCancelled){ // The status has just changed
            orderDTO.getItems().forEach(orderItem -> {
                Optional<Item> itemRepo = itemRepository.findById(orderItem.getItem().getId());
                if(itemRepo.isPresent()){
                    itemRepo.get().setQuantity(itemRepo.get().getQuantity() + orderItem.getQuantity());
                    itemRepository.save(itemRepo.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
            });

            order.setStatus(Status.TERMINEE.toString());
        }
        orderRepository.save(order);
    }

    /**
     * Update status.
     *
     * @param id     the id
     * @param status the status
     */
    public void updateStatus(Long id, String status) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean reqOrderIsCancelled = status.equals(Status.ANNULEE.toString());
        boolean repoOrderIsCancelled = order.getStatus().equals(Status.ANNULEE.toString());

        // update storage
        if(reqOrderIsCancelled && !repoOrderIsCancelled){ // The status has just changed
            order.getOrderItems().forEach(orderItem -> {

                Optional<Item> itemRepo = itemRepository.findById(orderItem.getItem().getId());
                if(itemRepo.isPresent()) {
                    itemRepo.get().setQuantity(itemRepo.get().getQuantity() + orderItem.getQuantity());
                    itemRepository.save(itemRepo.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
            });

            order.setStatus(Status.ANNULEE.toString());
        }
        if(status.equals("TERMINEE"))
        {
            order.setStatus(Status.TERMINEE.toString());
        }
        orderRepository.save(order);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    /**
     * Get sum price for month double.
     *
     * @param yearMonthDate the year month date
     * @return the double
     */
    public Double getSumPriceForMonth(String yearMonthDate){
        return orderRepository.findSumPriceForMonth(yearMonthDate);
    }

    /**
     * Get status nb list.
     *
     * @return the list
     */
    public List<MapProjection> getStatusNb(){
        return orderRepository.findStatusNb();
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {

        orderDTO.setId(order.getId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setAddress(order.getAddress());
        orderDTO.setPrice(order.getPrice());
        UserDTO userDTO = UserDTO.builder().username(order.getUser().getUsername()).role(order.getUser().getRole()).id(order.getUser().getId()).build();
        orderDTO.setUser(order.getUser() == null ? null : userDTO);
        orderDTO.setDatecreated(order.getDateCreated());
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object userPrincipal = (auth != null) ? auth.getPrincipal() :  null;
        User user = (userPrincipal instanceof User) ? (User) userPrincipal : null;

        order.setStatus(orderDTO.getStatus());
        order.setAddress(orderDTO.getAddress());
        order.setPrice(orderDTO.getPrice());
        order.setDateCreated(orderDTO.getDatecreated());
        order.setUser(user);
        order.setOrderItems(null);
        return order;
    }

    /**
     * To singleton collector.
     *
     * @param <T> the type parameter
     * @return the collector
     */
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