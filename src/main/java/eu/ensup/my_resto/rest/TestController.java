package eu.ensup.my_resto.rest;

import eu.ensup.my_resto.model.LoginDTO;
import eu.ensup.my_resto.model.RegisterDTO;
import eu.ensup.my_resto.service.AuthService;
import eu.ensup.my_resto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class TestController {

    @Autowired
    private AuthService authService;

    @GetMapping("")
    public String viewhomePage() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(((UserDetails)principal).getUsername());
        return "index";
    }

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
