package com.example.python_api.service;

import com.example.python_api.model.Link;
import com.example.python_api.model.Movie;
import com.example.python_api.model.Rating;
import com.example.python_api.model.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieDataService {

    private final CsvService csvService;

    public List<Movie> getMovies() {
        return csvService.loadCsv("movies.csv", parts ->
                new Movie(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2]
                ), 1);
    }

    public List<Link> getLinks() {
        return csvService.loadCsv("links.csv", parts ->
                new Link(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2])
                ), 1);
    }

    public List<Rating> getRatings() {
        return csvService.loadCsv("ratings.csv", parts ->
                new Rating(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        Double.parseDouble(parts[2]),
                        Long.parseLong(parts[3])
                ), 1);
    }

    public List<Tag> getTags() {
        return csvService.loadCsv("tags.csv", parts ->
                new Tag(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        parts[2],
                        Long.parseLong(parts[3])
                ), 1);
    }
}