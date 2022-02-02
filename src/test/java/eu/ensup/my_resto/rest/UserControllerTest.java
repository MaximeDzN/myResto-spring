package eu.ensup.my_resto.rest;

import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.model.Roles;
import eu.ensup.my_resto.model.UserDTO;
import eu.ensup.my_resto.repos.UserRepository;
import eu.ensup.my_resto.service.UserService;
import io.swagger.v3.core.util.Json;
import jdk.dynalink.linker.support.Guards;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import java.util.Locale;
import java.util.Properties;


@SpringBootTest
@AutoConfigureMockMvc
 class UserRepositoryTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }


    @Test
    void createUser() throws Exception{
        UserDTO testUser = new UserDTO();
        testUser.setUsername("yamine");
        testUser.setId(321L);
        testUser.setPassword("123456");
        testUser.setRole(String.valueOf(Roles.USER));

         mockMvc.perform( MockMvcRequestBuilders
                .post("/users")
                .accept(String.valueOf(MediaType.APPLICATION_JSON))
                .contentType("application/json, application/*+json")
                .content(asJsonString(testUser)))
                 .andExpect(status().isOk());

    }

    @Test
    void getUser() throws Exception {
        User testUser = new User();
        testUser.setUsername("yamine");
        testUser.setId(321L);
        testUser.setPassword("123456");
        testUser.setRole(String.valueOf(Roles.USER));

        userRepository.save(testUser);
        mockMvc.perform(get("/users/321"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void updateUser() {

    }

    @Test
    void deleteUser() {
    }



    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}