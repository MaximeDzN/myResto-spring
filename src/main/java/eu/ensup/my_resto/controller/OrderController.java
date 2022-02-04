package eu.ensup.my_resto.controller;

import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.model.OrderDTO;
import eu.ensup.my_resto.model.OrderItemsDTO;
import eu.ensup.my_resto.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String renderOrderPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<OrderDTO> orderDTOList;
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("OWNER"))) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            orderDTOList = orderService.findAll();
            model.addAttribute("sumMonth",orderService.getSumPriceForMonth(simpleDateFormat.format(new Date())));
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
    public String createOrder(@ModelAttribute("orderForm") @Valid final OrderDTO orderDTO, HttpServletRequest req, Model model) {
        ArrayList< OrderItemsDTO> itemsDTOS = (ArrayList< OrderItemsDTO>)req.getSession().getAttribute("cart");
        orderDTO.setItems(itemsDTOS);
        orderService.create(orderDTO);
        model.addAttribute("success", "Commande créée avec succès!");
        return "redirect:/";
    }

    @PostMapping("/updateorders")
    public String updateOrder(@PathParam("id") Long id, @PathParam("status") String status) {
        orderService.updateStatus(id, status);
        return "redirect:/orders";
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable final Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
