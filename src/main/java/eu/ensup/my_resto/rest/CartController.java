package eu.ensup.my_resto.rest;

import eu.ensup.my_resto.model.CartItemDTO;
import eu.ensup.my_resto.model.ItemDTO;
import eu.ensup.my_resto.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class CartController {

    private ArrayList<CartItemDTO> cartItems = new ArrayList<>();
    private Double cartTotal;

    @Autowired
    private ItemService itemService ;

    @PutMapping("/cart")
    public ResponseEntity<Void> updateCartItem(@RequestBody CartItemDTO cartItemDTO, HttpServletRequest req){
        HttpSession session = req.getSession();
        Object cart = session.getAttribute("cart");
        if(cart == null){
            cartItems.clear();
            cartTotal = 0.0;
        }
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        cartItems.forEach(cartItem -> {
            if(cartItemDTO.getId() == cartItem.getId()){
                System.out.println(cartItem);
                ItemDTO itemDTO = itemService.get(cartItem.getId());
                cartItem.setQuantity(cartItemDTO.getQuantity());
                total.updateAndGet(value -> value + (itemDTO.getPrice() * cartItem.getQuantity()));
            }
        });

        cartTotal = total.get();
        session.setAttribute("cart", cartItems);
        session.setAttribute("total", cartTotal);
        System.out.println(session.getAttribute("total"));

        return  ResponseEntity.ok().build();
    }

    @PostMapping(name = "/add_cart", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addToCart(@RequestBody CartItemDTO cartItemDTO, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object cart = req.getSession().getAttribute("cart");
        if(cart == null){
            cartItems.clear();
            cartTotal = 0.0;
        }

        System.out.println(cartItemDTO.getId());
        ItemDTO itemDTO = itemService.get(cartItemDTO.getId());
        cartTotal += (itemDTO.getPrice() * cartItemDTO.getQuantity());
        cartItems.add(cartItemDTO);

        session.setAttribute("cart", cartItems);
        session.setAttribute("total", cartTotal);
        System.out.println(session.getAttribute("total"));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartItemDTO>> getAllCartItem(HttpServletRequest req){
        List<CartItemDTO> itemDTOList = (List<CartItemDTO>) req.getSession().getAttribute("cart");
        HttpSession session = req.getSession();
        return new ResponseEntity<>(itemDTOList, HttpStatus.OK) ;
    }

}
