package eu.ensup.my_resto.controller;


import eu.ensup.my_resto.repos.Projections.StatusMapProjection;
import eu.ensup.my_resto.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class StatsController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/stats")
    public String viewStatPage(Model model){
             model.addAttribute("nbStatus",orderService.getStatusNb());
        return "stats";
    }


}
