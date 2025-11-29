package com.example.python_api.service;

import com.example.python_api.exception.ResourceAlreadyExistsException;
import com.example.python_api.exception.ResourceNotFoundException;
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

    public Link getLinkById(int movieId) {
        return linkRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Link o ID " + movieId + " nie istnieje"));
    }

    public Link addNewLink(Link link) {
        linkRepository.findById(link.getMovieId())
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistsException(
                            "Link z movieId " + link.getMovieId() + " już istnieje"
                    );
                });

        return linkRepository.save(link);
    }

    public Link updateLink(Link link) {
        Link dbLink = linkRepository.findById(link.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Link o ID " + link.getMovieId() + " nie istnieje"));

        dbLink.setImdbId(link.getImdbId());
        dbLink.setTmdbId(link.getTmdbId());

        return linkRepository.save(dbLink);
    }

    public void deleteLinkById(int movieId) {
        linkRepository.deleteById(movieId);
    }

    public Movie getMovieById(int movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie o ID " + movieId + " nie istnieje"));
    }

    public Movie addNewMovie(Movie movie) {
        movieRepository.findById(movie.getMovieId())
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistsException(
                            "Link z movieId " + movie.getMovieId() + " już istnieje"
                    );
                });

        return movieRepository.save(movie);
    }

    public Movie updateMovie(Movie movie) {
        Movie dbMovie = movieRepository.findById(movie.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Link o ID " + movie.getMovieId() + " nie istnieje"));

        dbMovie.setGenres(movie.getGenres());
        dbMovie.setTitle(movie.getTitle());

        return movieRepository.save(dbMovie);
    }

    public void deleteMovieById(int movieId) {
        movieRepository.deleteById(movieId);
    }

    public Rating getRatingById(int movieId) {
        return ratingRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating o ID " + movieId + " nie istnieje"));
    }

    public Rating addNewRating(Rating rating) {
        ratingRepository.findById(rating.getMovieId())
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistsException(
                            "Link z movieId " + rating.getMovieId() + " już istnieje"
                    );
                });

        return ratingRepository.save(rating);
    }

    public Rating updateRating(Rating rating) {
        Rating dbRating = ratingRepository.findById(rating.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Rating o ID " + rating.getMovieId() + " nie istnieje"));

        dbRating.setRating(rating.getRating());
        dbRating.setUserId(rating.getUserId());

        return ratingRepository.save(dbRating);
    }

    public void deleteRatingById(int movieId) {
        ratingRepository.deleteById(movieId);
    }

    public Tag getTagById(int movieId) {
        return tagRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag o ID " + movieId + " nie istnieje"));
    }

    public Tag addNewTag(Tag tag) {
        tagRepository.findById(tag.getMovieId())
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistsException(
                            "Tag z movieId " + tag.getMovieId() + " już istnieje"
                    );
                });

        return tagRepository.save(tag);
    }

    public Tag updateTag(Tag tag) {
        Tag dbTag = tagRepository.findById(tag.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Tag o ID " + tag.getMovieId() + " nie istnieje"));

        dbTag.setTag(tag.getTag());
        dbTag.setUserId(tag.getUserId());

        return tagRepository.save(dbTag);
    }

    public void deleteTagById(int movieId) {
        tagRepository.deleteById(movieId);
    }
}