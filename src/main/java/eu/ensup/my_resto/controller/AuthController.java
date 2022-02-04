package eu.ensup.my_resto.controller;


import eu.ensup.my_resto.model.RegisterDTO;
import eu.ensup.my_resto.service.AuthService;
import eu.ensup.my_resto.service.exception.UserExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * The type Auth controller.
 */
@Controller
public class AuthController {
    /**
     * The Logger.
     */
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    /**
     * View register page string.
     *
     * @return the string
     */
    @GetMapping("register")
    public String viewRegisterPage() {
        return "register";
    }

    /**
     * Signup string.
     *
     * @param registerDTO   the register dto
     * @param bindingResult the binding result
     * @param model         the model
     * @return the string
     */
    @PostMapping("register")
    public String signup(@ModelAttribute("registerForm") @Valid RegisterDTO registerDTO, BindingResult bindingResult, Model model){
        try {
            authService.signup(registerDTO);
            return "redirect:login";
        } catch (UserExistsException e) {
            logger.error(String.format("%s : %s",e.getMessage(),registerDTO.getUsername()));
            model.addAttribute("err",e.getMessage());
            return "register";
        }

    }

    /**
     * View login page string.
     *
     * @return the string
     */
    @GetMapping("login")
    public String viewLoginPage(){
        return "login";
    }
}
