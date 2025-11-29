package com.example.python_api.controller;

import com.example.python_api.model.Movie;
import com.example.python_api.model.Rating;
import com.example.python_api.service.MovieDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final MovieDataService movieDataService;

    @GetMapping("/ratings")
    public List<Rating> getRatings() {
        return movieDataService.getRatings();
    }

    @GetMapping("/ratings/{movieId}")
    public Rating getRatingById(@PathVariable int movieId) {
        return movieDataService.getRatingById(movieId);
    }

    @PostMapping("/ratings")
    @ResponseStatus(HttpStatus.CREATED)
    public Rating addNew(@RequestBody Rating rating) {
        return movieDataService.addNewRating(rating);
    }

    @PutMapping("/ratings")
    public Rating updateRating(@RequestBody Rating rating) {
        return movieDataService.updateRating(rating);
    }

    @DeleteMapping("/ratings/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRatingById(@PathVariable int movieId) {
        movieDataService.deleteRatingById(movieId);
    }
}
