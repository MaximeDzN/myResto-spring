package eu.ensup.my_resto.rest;

import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.model.OrderDTO;
import eu.ensup.my_resto.service.OrderService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("OWNER"))){
            //TODO À TEST AVEC LA CONNEXION
            return ResponseEntity.ok(orderService.findAllByUser((User)auth.getPrincipal()));
        }
        return ResponseEntity.ok(orderService.findAll());
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

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable final Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
