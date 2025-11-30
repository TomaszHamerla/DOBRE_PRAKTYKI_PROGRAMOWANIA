package com.example.python_api.dto;

public record RegisterUserDto(
        String fullName,
        String email,
        String password
) {
}
