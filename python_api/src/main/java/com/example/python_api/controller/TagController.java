package com.example.python_api.controller;

import com.example.python_api.model.Tag;
import com.example.python_api.service.MovieDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final MovieDataService movieDataService;

    @GetMapping("/tags")
    public List<Tag> getTags() {
        return movieDataService.getTags();
    }

    @GetMapping("/tags/{movieId}")
    public Tag getTagById(@PathVariable int movieId) {
        return movieDataService.getTagById(movieId);
    }

    @PostMapping("/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public Tag addNew(@RequestBody Tag tag) {
        return movieDataService.addNewTag(tag);
    }

    @PutMapping("/tags")
    public Tag updateTag(@RequestBody Tag tag) {
        return movieDataService.updateTag(tag);
    }

    @DeleteMapping("/tags/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTagById(@PathVariable int movieId) {
        movieDataService.deleteTagById(movieId);
    }
}
