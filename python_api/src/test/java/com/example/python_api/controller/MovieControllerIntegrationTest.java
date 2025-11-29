package com.example.python_api.controller;

import com.example.python_api.model.Movie;
import com.example.python_api.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MovieControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();;

        List<Movie> movies = List.of(
                new Movie(1, "Movie A", "Action"),
                new Movie(2, "Movie B", "Comedy"),
                new Movie(3, "Movie C", "Drama")
        );

        movieRepository.saveAll(movies);
    }

    @Test
    void testGetMovies() throws Exception {
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].movieId").value(1))
                .andExpect(jsonPath("$[1].movieId").value(2));
    }

    @Test
    void testGetMovieById() throws Exception {
        mockMvc.perform(get("/movies/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId").value(2))
                .andExpect(jsonPath("$.title").value("Movie B"))
                .andExpect(jsonPath("$.genres").value("Comedy"));
    }

    @Test
    void testGetMovieByIdNotFound() throws Exception {
        mockMvc.perform(get("/movies/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddNewMovie() throws Exception {
        String json = """
                {
                    "movieId": 10,
                    "title": "New Movie",
                    "genres": "Sci-Fi"
                }
                """;

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.movieId").value(10))
                .andExpect(jsonPath("$.title").value("New Movie"))
                .andExpect(jsonPath("$.genres").value("Sci-Fi"));
    }

    @Test
    void testUpdateMovie() throws Exception {
        String json = """
                {
                    "movieId": 1,
                    "title": "Updated Title",
                    "genres": "Thriller"
                }
                """;

        mockMvc.perform(put("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId").value(1))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.genres").value("Thriller"));
    }

    @Test
    void testDeleteMovie() throws Exception {
        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNoContent());

        assertFalse(movieRepository.findById(1).isPresent());
    }
}
