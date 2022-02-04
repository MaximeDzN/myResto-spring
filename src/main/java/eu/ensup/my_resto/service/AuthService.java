package eu.ensup.my_resto.service;

import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.model.RegisterDTO;
import eu.ensup.my_resto.model.Roles;
import eu.ensup.my_resto.repos.UserRepository;
import eu.ensup.my_resto.service.exception.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signup(RegisterDTO registerDTO) throws UserExistsException {
        try {
            userRepository.save(mapToEntity(registerDTO, new User()));
        } catch (Exception e){
            throw  new UserExistsException("L'utilisateur existe déjà");
        }

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
