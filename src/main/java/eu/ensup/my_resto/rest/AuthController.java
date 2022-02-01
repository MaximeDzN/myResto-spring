package eu.ensup.my_resto.rest;


import eu.ensup.my_resto.model.LoginDTO;
import eu.ensup.my_resto.model.RegisterDTO;
import eu.ensup.my_resto.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService){
        this.authService = authService;
    }

    @PostMapping("signup")
    public ResponseEntity<Void> signup(@RequestBody RegisterDTO registerDTO){
        authService.signup(registerDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("signin")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(authService.signin(loginDTO));
    }




}
