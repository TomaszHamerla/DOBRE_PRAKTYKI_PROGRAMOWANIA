package com.example.python_api.controller;

import com.example.python_api.model.Movie;
import com.example.python_api.service.MovieDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieDataService movieDataService;

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return movieDataService.getMovies();
    }

    @GetMapping("/movies/{movieId}")
    public Movie getMovieById(@PathVariable int movieId) {
        return movieDataService.getMovieById(movieId);
    }

    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public Movie addNew(@RequestBody Movie movie) {
        return movieDataService.addNewMovie(movie);
    }

    @PutMapping("/movies")
    public Movie updateMovie(@RequestBody Movie movie) {
        return movieDataService.updateMovie(movie);
    }

    @DeleteMapping("/movies/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMovieById(@PathVariable int movieId) {
        movieDataService.deleteMovieById(movieId);
    }
}
