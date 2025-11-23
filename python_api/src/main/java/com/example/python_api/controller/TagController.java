package com.example.python_api.controller;

import com.example.python_api.model.Tag;
import com.example.python_api.service.MovieDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final MovieDataService movieDataService;

    @GetMapping("/tags")
    public List<Tag> getTags() {
        return movieDataService.getTags();
    }
}
