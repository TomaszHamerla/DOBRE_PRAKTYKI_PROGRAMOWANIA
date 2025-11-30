package com.example.python_api.controller;

import com.example.python_api.model.Link;
import com.example.python_api.repository.LinkRepository;
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
public class LinkControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LinkRepository linkRepository;

    @BeforeEach
    void setUp() {
        linkRepository.deleteAll();

        List<Link> links = List.of(
                new Link(1, 1, 1),
                new Link(2, 2, 2),
                new Link(3, 3, 3)
        );

        linkRepository.saveAll(links);
    }

    @Test
    void testGetLinks() throws Exception {
        mockMvc.perform(get("/links"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].movieId").value(1))
                .andExpect(jsonPath("$[1].movieId").value(2));
    }

    @Test
    void testGetLinkById() throws Exception {
        mockMvc.perform(get("/links/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId").value(2))
                .andExpect(jsonPath("$.imdbId").value(2))
                .andExpect(jsonPath("$.tmdbId").value(2));
    }

    @Test
    void testGetLinkByIdNotFound() throws Exception {
        mockMvc.perform(get("/links/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddNew() throws Exception {
        String newLinkJson = """
                {
                    "movieId": 10,
                    "imdbId": 100,
                    "tmdbId": 1000
                }
                """;

        mockMvc.perform(post("/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newLinkJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.movieId").value(10))
                .andExpect(jsonPath("$.imdbId").value(100))
                .andExpect(jsonPath("$.tmdbId").value(1000));
    }

    @Test
    void testUpdateLink() throws Exception {
        String updateJson = """
                {
                    "movieId": 1,
                    "imdbId": 111,
                    "tmdbId": 222
                }
                """;

        mockMvc.perform(put("/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId").value(1))
                .andExpect(jsonPath("$.imdbId").value(111))
                .andExpect(jsonPath("$.tmdbId").value(222));
    }

    @Test
    void testDeleteLink() throws Exception {
        mockMvc.perform(delete("/links/1"))
                .andExpect(status().isNoContent());

        assertFalse(linkRepository.findById(1).isPresent());
    }
}