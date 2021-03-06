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
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * The type Order controller.
 */
@Controller
public class OrderController {

    private final OrderService orderService;

    /**
     * Instantiates a new Order controller.
     *
     * @param orderService the order service
     */
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Render order page string.
     *
     * @param model the model
     * @return the string
     */
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
        orderDTOList.sort(Comparator.comparing(OrderDTO::getId).reversed());
        model.addAttribute("orderslist",orderDTOList);
        return "orders";
    }

    /**
     * Gets order.
     *
     * @param id the id
     * @return the order
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable final Long id) {
        return ResponseEntity.ok(orderService.get(id));
    }

    /**
     * Create order string.
     *
     * @param orderDTO the order dto
     * @param req      the req
     * @param model    the model
     * @return the string
     */
    @PostMapping("/orders")
    public String createOrder(@ModelAttribute("orderForm") @Valid final OrderDTO orderDTO, HttpServletRequest req, Model model) {
        ArrayList< OrderItemsDTO> itemsDTOS = (ArrayList< OrderItemsDTO>)req.getSession().getAttribute("cart");
        orderDTO.setPrice((Double) req.getSession().getAttribute("total"));
        orderDTO.setItems(itemsDTOS);

        orderService.create(orderDTO);
        req.getSession().setAttribute("cart", null);
        req.getSession().setAttribute("total", 0.0);
        model.addAttribute("success", "Commande cr????e avec succ??s!");
        return "redirect:/";
    }

    /**
     * Update order string.
     *
     * @param id     the id
     * @param status the status
     * @return the string
     */
    @PostMapping("/updateorders")
    public String updateOrder(@PathParam("id") Long id, @PathParam("status") String status) {
        orderService.updateStatus(id, status);
        return "redirect:/orders";
    }

    /**
     * Delete order response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable final Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
