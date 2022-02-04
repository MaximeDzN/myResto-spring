package eu.ensup.my_resto.controller;

import eu.ensup.my_resto.model.ItemDTO;
import eu.ensup.my_resto.model.OrderItemsDTO;
import eu.ensup.my_resto.service.ItemService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RestController
public class CartController {

    final private ArrayList<OrderItemsDTO> orderItemsDTOS = new ArrayList<>();
    private ArrayList<Double> cartTotal = new ArrayList<>();

    @Autowired
    private ItemService itemService ;

    @PostMapping("/addCart")
    public ResponseEntity<HttpStatus> addToCart(@RequestBody OrderItemsDTO orderItemDTO, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object cart = req.getSession().getAttribute("cart");
        if(cart == null){
            orderItemsDTOS.clear();
            cartTotal.clear();
        }

        AtomicReference<Boolean> exists = new AtomicReference<>(false);
        //  check if item exists
        orderItemsDTOS.forEach(orderItem -> {
            if (orderItemDTO.getItem().getId().equals(orderItem.getItem().getId())) { // if exist update with total
                exists.set(true);
                ItemDTO itemDTO = itemService.get(orderItem.getItem().getId());
                orderItem.setQuantity(orderItemDTO.getQuantity()+orderItem.getQuantity());
                orderItem.setItem(itemDTO);
                cartTotal.add(itemDTO.getPrice() * orderItem.getQuantity());
            }
        });

        // item does not exist add it
        if(!exists.get()){
            ItemDTO itemDTO = itemService.get(orderItemDTO.getItem().getId());
            cartTotal.add(itemDTO.getPrice() * orderItemDTO.getQuantity());
            orderItemDTO.setItem(itemDTO);
            orderItemsDTOS.add(orderItemDTO);
        }

        Double total = cartTotal.stream().reduce(0.0, Double::sum);
        session.setAttribute("cart", orderItemsDTOS);
        session.setAttribute("total", total);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/removeCart/{id}")
    public ResponseEntity<HttpStatus> removeFromCart(@PathVariable("id") Long id, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object cart = req.getSession().getAttribute("cart");
        if(cart == null){
            orderItemsDTOS.clear();
            cartTotal.clear();
        }

        for(int i = 0;  i <orderItemsDTOS.size(); i++){
            if(orderItemsDTOS.get(i).getItem().getId().equals(id)) {
                cartTotal.add(orderItemsDTOS.get(i).getItem().getPrice() * orderItemsDTOS.get(i).getQuantity());
                orderItemsDTOS.remove(i);
                break;
            }
        }
        Double total = cartTotal.stream().reduce(0.0, Double::sum);
        session.setAttribute("cart", orderItemsDTOS);
        session.setAttribute("total", total);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart")
    public ResponseEntity<List<OrderItemsDTO>> getAllCartItem(HttpServletRequest req){
        List<OrderItemsDTO> itemDTOList = (List<OrderItemsDTO>) req.getSession().getAttribute("cart");
        HttpSession session = req.getSession();
        return new ResponseEntity<>(itemDTOList, HttpStatus.OK) ;
    }

}
