package com.example.python_api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {
    private int movieId;
    private String title;
    private String genres;

    public Movie(int movieId, String title, String genres) {
        this.movieId = movieId;
        this.title = title;
        this.genres = genres;
    }
}
