package eu.ensup.my_resto.service;

import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.model.RegisterDTO;
import eu.ensup.my_resto.model.Roles;
import eu.ensup.my_resto.repos.UserRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.shaded.com.trilead.ssh2.auth.AuthenticationManager;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthServiceTest {
    @Mock
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Test
    void signup() {
        User user = new User();
        user.setUsername("admin");

        User userResult = new User();
        userResult.setUsername("admin");
         userResult.setPassword(authService.encodePassword("admin"));
        userResult.setRole(String.valueOf(Roles.USER));

        // GIVEN
        RegisterDTO userRegister = new RegisterDTO();
        userRegister.setUsername(user.getUsername());
        userRegister.setPassword(user.getPassword());
        userRegister.setId(user.getId());
        when(userRepository.save(user)).thenReturn(userResult);
        // WHEN
        authService.signup(userRegister);

        // THEN
        verify(userRepository).save(user);
    }



}