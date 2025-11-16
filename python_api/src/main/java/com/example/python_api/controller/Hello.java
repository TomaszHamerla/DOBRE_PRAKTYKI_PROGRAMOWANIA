package com.example.python_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Hello {

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("hello", "world");
    }
}
