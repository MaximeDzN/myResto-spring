package eu.ensup.my_resto.rest;

import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.model.OrderDTO;
import eu.ensup.my_resto.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<OrderDTO> orderDTOList;
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("OWNER"))) {
            orderDTOList = orderService.findAll();
        } else if(auth != null) {
            orderDTOList = orderService.findAllByUser((User) auth.getPrincipal());
        } else {
            return "login";
        }
        model.addAttribute("orderslist",orderDTOList);
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable final Long id) {
        return ResponseEntity.ok(orderService.get(id));
    }

    @PostMapping("/orders")
    public ResponseEntity<Long> createOrder(@RequestBody @Valid final OrderDTO orderDTO, HttpServletRequest req) {
        HttpSession session = req.getSession();

        return new ResponseEntity<>(orderService.create(orderDTO), HttpStatus.CREATED);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable final Long id,
            @RequestBody @Valid final OrderDTO orderDTO) {
        orderService.update(id, orderDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deleteorders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable final Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
