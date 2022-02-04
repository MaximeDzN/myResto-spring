package eu.ensup.my_resto.controller;


import eu.ensup.my_resto.service.ItemService;
import eu.ensup.my_resto.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * The type Stats controller.
 */
@Controller
public class StatsController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    /**
     * View stat page string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/stats")
    public String viewStatPage(Model model){
             model.addAttribute("nbStatus",orderService.getStatusNb());
             model.addAttribute("itemCategory",itemService.getNbItemCategory());
        return "stats";
    }


}
