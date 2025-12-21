package com.example.appkaodkolejki.config;

import jakarta.annotation.PostConstruct;
import nu.pattern.OpenCV;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenCVConfig {

    @PostConstruct
    public void loadOpenCV() {
        OpenCV.loadLocally();
        }
}
