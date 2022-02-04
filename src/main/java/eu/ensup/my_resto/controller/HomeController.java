package eu.ensup.my_resto.controller;

import eu.ensup.my_resto.model.ItemDTO;
import eu.ensup.my_resto.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Home controller.
 */
@Controller
public class HomeController {

    @Autowired
    private ItemService itemService;

    /**
     * Viewhome page string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/")
    public String viewhomePage(Model model) {
        var items = itemService.findAll();
        model.addAttribute("items",items);
        return "index";
    }
}
