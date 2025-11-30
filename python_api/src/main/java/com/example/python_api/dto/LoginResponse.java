package com.example.python_api.dto;

public record LoginResponse(
        String token,
        Long expiresIn
) {
}
