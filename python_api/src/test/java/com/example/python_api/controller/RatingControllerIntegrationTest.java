package com.example.python_api.controller;

import com.example.python_api.model.Rating;
import com.example.python_api.repository.RatingRepository;
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
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class RatingControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RatingRepository ratingRepository;

    @BeforeEach
    void setUp() {
        ratingRepository.deleteAll();

        List<Rating> ratings = List.of(
                new Rating(1, 1, 4.5, 111111111),
                new Rating(2, 2, 3.0, 222222222),
                new Rating(3, 3, 5.0, 333333333)
        );

        ratingRepository.saveAll(ratings);
    }

    @Test
    void testGetRatings() throws Exception {
        mockMvc.perform(get("/ratings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].movieId").value(1))
                .andExpect(jsonPath("$[1].movieId").value(2));
    }

    @Test
    void testGetRatingById() throws Exception {
        mockMvc.perform(get("/ratings/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId").value(2))
                .andExpect(jsonPath("$.rating").value(3.0))
                .andExpect(jsonPath("$.timestamp").value(222222222));
    }

    @Test
    void testGetRatingByIdNotFound() throws Exception {
        mockMvc.perform(get("/ratings/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddNewRating() throws Exception {
        String json = """
                {
                    "userId": 10,
                    "movieId": 10,
                    "rating": 4.0,
                    "timestamp": 999999999
                }
                """;

        mockMvc.perform(post("/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(10))
                .andExpect(jsonPath("$.movieId").value(10))
                .andExpect(jsonPath("$.rating").value(4.0))
                .andExpect(jsonPath("$.timestamp").value(999999999));
    }

    @Test
    void testUpdateRating() throws Exception {
        String json = """
                {
                    "userId": 1,
                    "movieId": 1,
                    "rating": 2.5,
                    "timestamp": 123456789
                }
                """;

        mockMvc.perform(put("/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.movieId").value(1))
                .andExpect(jsonPath("$.rating").value(2.5));
    }

    @Test
    void testDeleteRating() throws Exception {
        mockMvc.perform(delete("/ratings/1"))
                .andExpect(status().isNoContent());

        assertFalse(ratingRepository.findById(1).isPresent());
    }
}
