package com.example.python_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table
@NoArgsConstructor
@Entity
public class Movie {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;
    private String title;
    private String genres;

    public Movie(int movieId, String title, String genres) {
        this.movieId = movieId;
        this.title = title;
        this.genres = genres;
    }
}
