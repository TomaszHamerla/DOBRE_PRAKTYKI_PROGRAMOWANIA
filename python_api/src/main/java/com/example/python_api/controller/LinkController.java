package com.example.python_api.controller;

import com.example.python_api.model.Link;
import com.example.python_api.service.MovieDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LinkController {

    private final MovieDataService movieDataService;

    @GetMapping("/links")
    public List<Link> getLinks() {
        return movieDataService.getLinks();
    }

    @GetMapping("/links/{movieId}")
    public Link getLinkById(@PathVariable int movieId) {
        return movieDataService.getLinkById(movieId);
    }

    @PostMapping("/links")
    @ResponseStatus(HttpStatus.CREATED)
    public Link addNew(@RequestBody Link link) {
        return movieDataService.addNewLink(link);
    }

    @PutMapping("/links")
    public Link updateLink(@RequestBody Link link) {
        return movieDataService.updateLink(link);
    }

    @DeleteMapping("/links/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteLinkById(@PathVariable int movieId) {
        movieDataService.deleteLinkById(movieId);
    }
}
