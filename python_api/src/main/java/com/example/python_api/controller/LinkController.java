package com.example.python_api.controller;

import com.example.python_api.model.Link;
import com.example.python_api.service.MovieDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LinkController {

    private final MovieDataService movieDataService;

    @GetMapping("/links")
    public List<Link> getLinks() {
        return movieDataService.getLinks();
    }
}
