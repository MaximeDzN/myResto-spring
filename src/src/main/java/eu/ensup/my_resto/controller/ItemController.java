package eu.ensup.my_resto.controller;

import eu.ensup.my_resto.domain.Image;
import eu.ensup.my_resto.model.ItemDTO;
import eu.ensup.my_resto.model.RegisterDTO;
import eu.ensup.my_resto.service.ItemService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import eu.ensup.my_resto.service.exception.FileNotDeleted;
import eu.ensup.my_resto.service.exception.FileNotSaved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("items")
public class ItemController {

    Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private  ItemService itemService;


    @GetMapping("/{id}")
    public String getItem(@PathVariable final Long id, Model model) {
        ItemDTO item = itemService.get(id);
        List<ItemDTO> itemDTOList = new ArrayList<>();
        itemDTOList.add(item);
        model.addAttribute("items", itemDTOList);
        return "product";
    }

    @GetMapping("/add")
    public String addItemView(Model model){
        return "addItem";
    }

    @PostMapping("/add")
    public String createItemForm(@ModelAttribute("itemForm") @Valid ItemDTO itemDTO,Model model){
        try {
            itemDTO.setImage(Image.builder().path(Base64Utils.encodeToString(itemDTO.getFile().getBytes())).build());
            itemService.create(itemDTO);
        } catch (IOException | FileNotSaved e) {
            logger.error(e.getMessage());
            model.addAttribute("err",e.getMessage());
        }

        return "addItem";
    }


    @PostMapping
    public ResponseEntity<Long> createItem(@RequestBody @Valid final ItemDTO itemDTO) {
        try {
            return new ResponseEntity<>(itemService.create(itemDTO), HttpStatus.CREATED);
        } catch (FileNotSaved e) {
            logger.error(e.getMessage());
            return  ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable final Long id,
                                           @RequestBody @Valid final ItemDTO itemDTO) {
        itemService.update(id, itemDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
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
