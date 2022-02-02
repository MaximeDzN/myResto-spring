package eu.ensup.my_resto.rest;


import eu.ensup.my_resto.model.LoginDTO;
import eu.ensup.my_resto.model.RegisterDTO;
import eu.ensup.my_resto.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("register")
    public String viewRegisterPage() {
        return "register";
    }

    @PostMapping("register")
    public String signup(@ModelAttribute("registerForm") @Valid RegisterDTO registerDTO, BindingResult bindingResult){
        authService.signup(registerDTO);
        return "redirect:/";
    }

    @GetMapping("login")
    public String viewLoginPage(){
        return "login";
    }
}
