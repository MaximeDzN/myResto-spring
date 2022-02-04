package eu.ensup.my_resto.controller;

import eu.ensup.my_resto.model.ItemDTO;
import eu.ensup.my_resto.model.OrderItemsDTO;
import eu.ensup.my_resto.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class CartController {

    final private ArrayList<OrderItemsDTO> orderItemsDTOS = new ArrayList<>();
    private Double cartTotal;

    @Autowired
    private ItemService itemService ;

    @PostMapping()

    @PutMapping("/cart")
    public ResponseEntity<Void> updateCartItem(@RequestBody OrderItemsDTO orderItemDTO, HttpServletRequest req){
        HttpSession session = req.getSession();
        Object cart = session.getAttribute("cart");
        if(cart == null){
            orderItemsDTOS.clear();
            cartTotal = 0.0;
        }
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        orderItemsDTOS.forEach(orderItem -> {
            if(orderItemDTO.getItem().getId().equals(orderItem.getItem().getId())){
                ItemDTO itemDTO = itemService.get(orderItem.getItem().getId());
                orderItem.setQuantity(orderItemDTO.getQuantity());
                orderItem.setItem(itemDTO);
                total.updateAndGet(value -> value + (itemDTO.getPrice() * orderItem.getQuantity()));
            }
        });

        cartTotal = total.get();
        session.setAttribute("cart", orderItemsDTOS);
        session.setAttribute("total", cartTotal);
        System.out.println(session.getAttribute("total"));

        return  ResponseEntity.ok().build();
    }

    @PostMapping("/addCart")
    public ResponseEntity<HttpStatus> addToCart(@RequestBody OrderItemsDTO orderItemDTO, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object cart = req.getSession().getAttribute("cart");
        if(cart == null){
            orderItemsDTOS.clear();
            cartTotal = 0.0;
        }

        ItemDTO itemDTO = itemService.get(orderItemDTO.getItem().getId());
        cartTotal += (itemDTO.getPrice() * orderItemDTO.getQuantity());
        orderItemDTO.setItem(itemDTO);
        orderItemsDTOS.add(orderItemDTO);

        session.setAttribute("cart", orderItemsDTOS);
        session.setAttribute("total", cartTotal);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/removeCart/{id}")
    public ResponseEntity<HttpStatus> removeFromCart(@PathVariable("id") Long id, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object cart = req.getSession().getAttribute("cart");
        if(cart == null){
            orderItemsDTOS.clear();
            cartTotal = 0.0;
        } else{
            orderItemsDTOS.removeIf(item-> {
                cartTotal -= (item.getItem().getPrice() * item.getQuantity());
                return item.getItem().getId().equals(id);
            });
        }

        session.setAttribute("cart", orderItemsDTOS);
        session.setAttribute("total", cartTotal);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart")
    public ResponseEntity<List<OrderItemsDTO>> getAllCartItem(HttpServletRequest req){
        List<OrderItemsDTO> itemDTOList = (List<OrderItemsDTO>) req.getSession().getAttribute("cart");
        HttpSession session = req.getSession();
        return new ResponseEntity<>(itemDTOList, HttpStatus.OK) ;
    }

}
