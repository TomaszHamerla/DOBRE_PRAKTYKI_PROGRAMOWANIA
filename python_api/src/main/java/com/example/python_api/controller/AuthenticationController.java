package com.example.python_api.controller;

import com.example.python_api.dto.LoginResponse;
import com.example.python_api.dto.LoginUserDto;
import com.example.python_api.dto.RegisterUserDto;
import com.example.python_api.model.User;
import com.example.python_api.security.JwtService;
import com.example.python_api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User register(@RequestBody RegisterUserDto registerUserDto) {
        return authenticationService.signup(registerUserDto);
    }

    @PostMapping("/login")
    public LoginResponse authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        return new LoginResponse(jwtToken, jwtService.getExpirationTime());
    }

    @GetMapping("/details")
    public Map<String, Object> getUserDetails(@RequestHeader(name = "Authorization") String authorizationHeader) {
        authorizationHeader = authorizationHeader.replace("Bearer ", "");
        String payload = new String(Base64.getUrlDecoder().decode(authorizationHeader.split("\\.")[1]), StandardCharsets.UTF_8);

        Map<String, Object> response = new HashMap<>();
        response.put("userDetails", payload);

        return response;
    }
}
