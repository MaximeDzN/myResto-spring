package eu.ensup.my_resto.rest;

import eu.ensup.my_resto.model.ItemDTO;
import eu.ensup.my_resto.service.ItemService;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import eu.ensup.my_resto.service.exception.FileNotDeleted;
import eu.ensup.my_resto.service.exception.FileNotSaved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private  ItemService itemService;


    @GetMapping("/items")
    public String getAllItems(Model model) {
        var items = itemService.findAll();
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

    @PostMapping("/items")
    public ResponseEntity<Long> createItem(@RequestBody @Valid final ItemDTO itemDTO) {
        try {
            return new ResponseEntity<>(itemService.create(itemDTO), HttpStatus.CREATED);
        } catch (FileNotSaved e) {
            logger.error(e.getMessage());
            return  ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable final Long id,
                                           @RequestBody @Valid final ItemDTO itemDTO) {
        itemService.update(id, itemDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable final Long id) {
        try {
            itemService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (FileNotDeleted e) {
            logger.error(e.getMessage());
            return  ResponseEntity.internalServerError().build();
        }

    }

}
