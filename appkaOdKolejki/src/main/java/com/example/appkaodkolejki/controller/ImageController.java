package com.example.appkaodkolejki.controller;

import com.example.appkaodkolejki.service.ImageAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageAnalysisService analysisService;

    @GetMapping("/analyze_img")
    public ResponseEntity<?> analyzeImage(@RequestParam String url) {
        try {
            long startTime = System.currentTimeMillis();

            int count = analysisService.countPeople(url);

            long duration = System.currentTimeMillis() - startTime;

            return ResponseEntity.ok(Map.of(
                    "people_detected", count,
                    "processing_time_ms", duration,
                    "status", "success"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
