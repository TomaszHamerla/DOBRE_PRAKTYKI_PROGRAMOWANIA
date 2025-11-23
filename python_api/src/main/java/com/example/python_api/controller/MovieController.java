package com.example.python_api.controller;

import com.example.python_api.model.Movie;
import com.example.python_api.service.MovieDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieDataService movieDataService;

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return movieDataService.getMovies();
    }
}
