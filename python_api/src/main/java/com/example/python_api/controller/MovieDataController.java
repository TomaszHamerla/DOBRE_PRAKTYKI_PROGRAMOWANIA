package com.example.python_api.controller;

import com.example.python_api.model.Link;
import com.example.python_api.model.Movie;
import com.example.python_api.model.Rating;
import com.example.python_api.model.Tag;
import com.example.python_api.service.MovieDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieDataController {

    private final MovieDataService movieDataService;

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return movieDataService.getMovies();
    }

    @GetMapping("/links")
    public List<Link> getLinks() {
        return movieDataService.getLinks();
    }

    @GetMapping("/ratings")
    public List<Rating> getRatings() {
        return movieDataService.getRatings();
    }

    @GetMapping("/tags")
    public List<Tag> getTags() {
        return movieDataService.getTags();
    }
}
