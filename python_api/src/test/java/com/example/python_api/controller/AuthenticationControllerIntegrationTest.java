package com.example.python_api.controller;

import com.example.python_api.dto.LoginUserDto;
import com.example.python_api.dto.RegisterUserDto;
import com.example.python_api.model.Role;
import com.example.python_api.model.User;
import com.example.python_api.repository.UserRepository;
import com.example.python_api.security.JwtService;
import com.example.python_api.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    User userAdmin;
    User user;

    String password = "password";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        userAdmin = new User();
        userAdmin.setFullName("Test User");
        userAdmin.setEmail("test@gmail.com");
        userAdmin.setPassword(passwordEncoder.encode(password));
        userAdmin.setRole(Role.ADMIN);

        user = new User();
        user.setFullName("Test User");
        user.setEmail("user@gmail.com");
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);

        userRepository.save(user);
        userRepository.save(userAdmin);
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {
        LoginUserDto loginDto = new LoginUserDto(userAdmin.getEmail(), password);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.expiresIn").isNumber());
    }

    @Test
    void shouldFailLoginWithBadCredentials() throws Exception {
        LoginUserDto loginDto = new LoginUserDto("invalid", "wrongpassword");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldRegisterUserWithAdminAuthority() throws Exception {
        RegisterUserDto registerDto = new RegisterUserDto("new_user_by_admin", "secure_pass", "USER");

        String adminToken = performLoginAndGetToken(userAdmin.getEmail(), password);

        mockMvc.perform(post("/auth/signup")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("new_user_by_admin"));
    }

    @Test
    void shouldFailRegisterUserWithoutAuthority() throws Exception {
        RegisterUserDto registerDto = new RegisterUserDto("unauthorized_user", "pass", "USER");

        String userToken = performLoginAndGetToken(user.getEmail(), password);

        mockMvc.perform(post("/auth/signup")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnUserDetailsWithValidToken() throws Exception {
        String validToken = performLoginAndGetToken(user.getEmail(), password);

        mockMvc.perform(get("/auth/details")
                        .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userDetails", containsString(user.getEmail())));
    }

    @Test
    void shouldFailToReturnUserDetailsWithoutToken() throws Exception {
        mockMvc.perform(get("/auth/details"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailToReturnUserDetailsWithMalformedToken() throws Exception {
        mockMvc.perform(get("/auth/details")
                        .header("Authorization", "invalid"))
                .andExpect(status().isInternalServerError());
    }

    private String performLoginAndGetToken(String username, String password) throws Exception {
        LoginUserDto loginDto = new LoginUserDto(username, password);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        return objectMapper.readTree(responseContent).get("token").asText();
    }
}
