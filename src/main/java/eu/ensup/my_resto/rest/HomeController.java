package eu.ensup.my_resto.rest;

import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.model.OrderDTO;
import eu.ensup.my_resto.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String viewhomePage(Model model) {
        List<OrderDTO> orderDTOList;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("OWNER"))) {
            orderDTOList = orderService.findAllByUser((User) auth.getPrincipal());
        } else {
            orderDTOList = orderService.findAll();
        }
        System.out.println(orderDTOList);
        model.addAttribute("orders",orderDTOList);
        return "index";
    }
}
