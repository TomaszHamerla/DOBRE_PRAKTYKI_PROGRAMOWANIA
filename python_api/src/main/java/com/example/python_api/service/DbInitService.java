package com.example.python_api.service;

import com.example.python_api.model.Link;
import com.example.python_api.model.Movie;
import com.example.python_api.model.Rating;
import com.example.python_api.model.Tag;
import com.example.python_api.repository.LinkRepository;
import com.example.python_api.repository.MovieRepository;
import com.example.python_api.repository.RatingRepository;
import com.example.python_api.repository.TagRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbInitService {

    private final LinkRepository linkRepository;
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private final TagRepository tagRepository;
    private final MovieDataService movieDataService;

    @PostConstruct
    public void initDb() {
        Optional<Link> link = linkRepository.findById(1);
        if (link.isEmpty()) {
            fetchLinks();
        }

        Optional<Movie> movie = movieRepository.findById(1);
        if (movie.isEmpty()) {
            fetchMovies();
        }

        Optional<Rating> rating = ratingRepository.findById(1);
        if (rating.isEmpty()) {
            fetchRatings();
        }

        Optional<Tag> tag = tagRepository.findById(2);
        if (tag.isEmpty()) {
            fetchTags();
        }
    }

    private void fetchTags() {
        List<Tag> tags = movieDataService.getTags();
        tagRepository.saveAll(tags);
    }

    private void fetchRatings() {
        List<Rating> ratings = movieDataService.getRatings();
        ratingRepository.saveAll(ratings);
    }

    private void fetchMovies() {
        List<Movie> movies = movieDataService.getMovies();
        movieRepository.saveAll(movies);
    }

    private void fetchLinks() {
        List<Link> links = movieDataService.getLinks();
        linkRepository.saveAll(links);
    }
}
