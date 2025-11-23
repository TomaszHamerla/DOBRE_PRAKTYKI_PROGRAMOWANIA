package com.example.python_api.controller;

import com.example.python_api.model.Rating;
import com.example.python_api.service.MovieDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final MovieDataService movieDataService;

    @GetMapping("/ratings")
    public List<Rating> getRatings() {
        return movieDataService.getRatings();
    }
}
