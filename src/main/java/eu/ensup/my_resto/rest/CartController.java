package eu.ensup.my_resto.rest;

import eu.ensup.my_resto.model.CartItemDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class CartController {

    private ArrayList<CartItemDTO> cartItems = new ArrayList<>();

    @PutMapping("/cart")
    public ResponseEntity<Void> updateCartItem(@RequestBody CartItemDTO cartItemDTO, HttpServletRequest req){
        HttpSession session = req.getSession();
        Object cart = session.getAttribute("cart");
        if(cart == null){
            cartItems.clear();
        }
        cartItems.forEach(cartItem -> {
            if(cartItemDTO.getId() == cartItem.getId()){
                cartItem.setQuantity(cartItemDTO.getQuantity());
            }
        });

        return  ResponseEntity.ok().build();
    }

    @PostMapping("/cart")
    public ResponseEntity<Void> addToCart(@RequestBody CartItemDTO cartItemDTO, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object cart = req.getSession().getAttribute("cart");
        if(cart == null){
            cartItems.clear();
        }

        cartItems.add(cartItemDTO);
        session.setAttribute("cart", cartItems);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartItemDTO>> getAllCartItem(HttpServletRequest req){
        List<CartItemDTO> itemDTOList = (List<CartItemDTO>) req.getSession().getAttribute("cart");
        return new ResponseEntity<>(itemDTOList, HttpStatus.OK) ;
    }
}
