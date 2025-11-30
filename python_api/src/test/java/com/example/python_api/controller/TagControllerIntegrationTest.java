package com.example.python_api.controller;

import com.example.python_api.model.Tag;
import com.example.python_api.repository.TagRepository;
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
public class TagControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        tagRepository.deleteAll();

        List<Tag> tags = List.of(
                new Tag(1, 1, "funny", 111111111),
                new Tag(2, 2, "dark", 222222222),
                new Tag(3, 3, "classic", 333333333)
        );

        tagRepository.saveAll(tags);
    }

    @Test
    void testGetTags() throws Exception {
        mockMvc.perform(get("/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].movieId").value(1))
                .andExpect(jsonPath("$[1].movieId").value(2));
    }

    @Test
    void testGetTagById() throws Exception {
        mockMvc.perform(get("/tags/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId").value(2))
                .andExpect(jsonPath("$.tag").value("dark"))
                .andExpect(jsonPath("$.timestamp").value(222222222));
    }

    @Test
    void testGetTagByIdNotFound() throws Exception {
        mockMvc.perform(get("/tags/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddNewTag() throws Exception {
        String json = """
                {
                    "userId": 10,
                    "movieId": 10,
                    "tag": "epic",
                    "timestamp": 999999999
                }
                """;

        mockMvc.perform(post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(10))
                .andExpect(jsonPath("$.movieId").value(10))
                .andExpect(jsonPath("$.tag").value("epic"))
                .andExpect(jsonPath("$.timestamp").value(999999999));
    }

    @Test
    void testUpdateTag() throws Exception {
        String json = """
                {
                    "userId": 1,
                    "movieId": 1,
                    "tag": "updated-tag",
                    "timestamp": 123456789
                }
                """;

        mockMvc.perform(put("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.movieId").value(1))
                .andExpect(jsonPath("$.tag").value("updated-tag"));
    }

    @Test
    void testDeleteTag() throws Exception {
        mockMvc.perform(delete("/tags/1"))
                .andExpect(status().isNoContent());

        assertFalse(tagRepository.findById(1).isPresent());
    }
}
