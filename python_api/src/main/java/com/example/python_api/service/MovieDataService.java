package com.example.python_api.service;

import com.example.python_api.model.Link;
import com.example.python_api.model.Movie;
import com.example.python_api.model.Rating;
import com.example.python_api.model.Tag;
import com.example.python_api.repository.LinkRepository;
import com.example.python_api.repository.MovieRepository;
import com.example.python_api.repository.RatingRepository;
import com.example.python_api.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieDataService {

   // private final CsvService csvService;

    private final LinkRepository linkRepository;
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private final TagRepository tagRepository;

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public List<Link> getLinks() {
        return linkRepository.findAll();
    }

    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }
}