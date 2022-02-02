package eu.ensup.my_resto.rest;

import eu.ensup.my_resto.model.ItemDTO;
import eu.ensup.my_resto.service.ItemService;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(final ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String getAllItems(Model model) {
        var items = itemService.findAll();
        System.out.println(items);
        model.addAttribute("items",items);
        return "product";
    }

    @GetMapping("/items/{id}")
    public String getItem(@PathVariable final Long id, Model model) {
        ItemDTO item = itemService.get(id);
        List<ItemDTO> itemDTOList = new ArrayList<>();
        itemDTOList.add(item);
        model.addAttribute("items", itemDTOList);
        return "product";
    }

    @PostMapping
    public ResponseEntity<Long> createItem(@RequestBody @Valid final ItemDTO itemDTO) {
        return new ResponseEntity<>(itemService.create(itemDTO), HttpStatus.CREATED);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable final Long id,
                                           @RequestBody @Valid final ItemDTO itemDTO) {
        itemService.update(id, itemDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable final Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
