package eu.ensup.my_resto.service;

import eu.ensup.my_resto.domain.Item;
import eu.ensup.my_resto.domain.Order;
import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.model.OrderDTO;
import eu.ensup.my_resto.repos.ItemRepository;
import eu.ensup.my_resto.repos.OrderRepository;
import eu.ensup.my_resto.repos.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Transactional
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public OrderService(final OrderRepository orderRepository, final UserRepository userRepository,
            final ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
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
        return orderRepository.save(order).getId();
    }

    public void update(final Long id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(orderDTO, order);
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
        orderDTO.setItems(order.getItems() == null ? null : order.getItems().stream()
                .map(item -> item.getId())
                .collect(Collectors.toList()));
        return orderDTO;
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
        if (orderDTO.getItems() != null) {
            final List<Item> items = itemRepository.findAllById(orderDTO.getItems());
            if (items.size() != orderDTO.getItems().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of items not found");
            }
            order.setItems(items.stream().collect(Collectors.toSet()));
        }
        return order;
    }

}
