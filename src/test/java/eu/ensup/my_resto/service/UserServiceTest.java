package eu.ensup.my_resto.service;

import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.model.Roles;
import eu.ensup.my_resto.repos.UserRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Test load an user if exist return an user")
    void getUser() {

        User testUser = new User();
        testUser.setUsername("gérant");
        testUser.setId(321L);
        testUser.setPassword("123456");
        testUser.setRole(String.valueOf(Roles.USER));
        userRepository.save(testUser);

        // GIVEN
        when(userRepository.findByUsername("gérant")).thenReturn(java.util.Optional.of(testUser));
        // WHEN
        final String result = userService.loadUserByUsername("gérant").toString();
        MatcherAssert.assertThat("Test fail : ", result, Matchers.equalTo(testUser.toString()));
        // THEN
        verify(userRepository).findByUsername("gérant");
    }


    @Test
    @DisplayName("Test load an user if not exist ")
    void TestExceptionLoadUserByUsername() {

        // GIVEN
        when(userRepository.findByUsername("gérant")).thenReturn(Optional.empty());
        // WHEN
        final String result = userService.loadUserByUsername("gérant").toString();
        MatcherAssert.assertThat("Test fail : ", result, Matchers.equalTo(Optional.empty()));
        // THEN
        verify(userRepository).findAll();
    }


}


