package eu.ensup.my_resto.rest;


import eu.ensup.my_resto.model.LoginDTO;
import eu.ensup.my_resto.model.RegisterDTO;
import eu.ensup.my_resto.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("signup")
    public ResponseEntity<Void> signup(@RequestBody RegisterDTO registerDTO){
        authService.signup(registerDTO);
        return ResponseEntity.ok().build();
    }





}
