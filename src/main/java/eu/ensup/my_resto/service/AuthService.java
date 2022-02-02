package eu.ensup.my_resto.service;

import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.model.LoginDTO;
import eu.ensup.my_resto.model.RegisterDTO;
import eu.ensup.my_resto.model.Roles;
import eu.ensup.my_resto.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signup(RegisterDTO registerDTO) {
        userRepository.save(mapToEntity(registerDTO, new User()));
    }

    public String signin(LoginDTO loginDTO) {
        return "pwet";
    }


    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private User mapToEntity(final RegisterDTO registerDTO, final User user) {
        user.setUsername(registerDTO.getUsername());
        user.setPassword(encodePassword(registerDTO.getPassword()));
        user.setRole(String.valueOf(Roles.USER));
        return user;
    }


}
