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

    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        order.setStatus(Status.EN_ATTENTE.toString());
        Long id = orderRepository.save(order).getId();
        order.setId(id);
        final List<Item> items = itemRepository.findAllById(orderDTO.getItems().stream().map(orderItemsDTO -> orderItemsDTO.getItem().getId()).collect(Collectors.toList()));
        final Set<OrderItem> orderItems = items.stream().map(item -> OrderItem
                        .builder()
                        .order(order)
                        .item(item)
                        .quantity(orderDTO.getItems().stream().filter(item1 -> item1.getItem().getId() == item.getId()).map(item2 -> item2.getQuantity()).collect(toSingleton()))
                        .build())
                .collect(Collectors.toSet());
        orderItemRepository.saveAll(orderItems);
        return id;
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
        List<OrderItem> items = orderItemRepository.findAll().stream().filter(orderItem -> orderItem.getOrder().getId() == order.getId()).collect(Collectors.toList());
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
        if (orderDTO.getItems() != null) {
            final List<Item> items = itemRepository.findAllById(orderDTO.getItems().stream().map(orderItemsDTO -> orderItemsDTO.getItem().getId()).collect(Collectors.toList()));
            System.out.println(items);
            if (items.size() != orderDTO.getItems().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of items not found");
            }
            order.setOrderItems(null);
        }
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

/**
 * L'utilisation d'un Stream implique généralement trois choses :
 *
 * Une source qui va alimenter le Stream avec les éléments à traiter
 * Un ensemble d'opérations intermédiaires qui vont décrire les traitements à effectuer
 * Une opération terminale qui va exécuter les opérations et produire le résultat
 * Les opérations proposées par un Stream sont des opérations communes :
 *
 * Pour filtrer des données
 * Pour rechercher une correspondance avec des éléments
 * Pour transformer des éléments
 * Pour réduire les éléments et produire un résultat
 * Pour filtrer des données, un Stream propose plusieurs opérations :
 *
 * filter(Predicate) : renvoie un Stream qui contient les éléments pour lesquels l'évaluation du Predicate passé en paramètre vaut true
 * distinct() : renvoie un Stream qui ne contient que les éléments uniques (elle retire les doublons). La comparaison se fait grâce à l'implémentation de la méthode equals()
 * limit(n) : renvoie un Stream que ne contient comme éléments que le nombre fourni en paramètre
 * skip(n) : renvoie un Stream dont les n premiers éléments sont ignorés
 *
 * Pour rechercher une correspondance avec des éléments, un Stream propose plusieurs opérations :
 *
 * anyMatch(Predicate) : renvoie un booléen qui précise si l'évaluation du Predicate sur au moins un élément vaut true
 * allMatch(Predicate) : renvoie un booléen qui précise si l'évaluation du Predicate sur tous les éléments vaut true
 * noneMatch(Predicate) : renvoie un booléen qui précise si l'évaluation du Predicate sur tous les éléments vaut false
 * findAny() : renvoie un objet de type Optional qui encapsule un élément du Stream s'il existe
 * findFirst() : renvoie un objet de type Optional qui encapsule le premier élément du Stream s'il existe
 *
 * Pour transformer des données, un Stream propose plusieurs opérations :
 *
 * map(Function) : applique la Function fournie en paramètre pour transformer l'élément en créant un nouveau
 * flatMap(Function) : applique la Function fournie en paramètre pour transformer l'élément en créant zéro, un ou plusieurs éléments
 *
 * Pour réduire les données et produire un résultat, un Stream propose plusieurs opérations :
 *
 * reduce() : applique une Function pour combiner les éléments afin de produire le résultat
 * collect() : permet de transformer un Stream qui contiendra le résultat des traitements de réduction dans un conteneur mutable
 */