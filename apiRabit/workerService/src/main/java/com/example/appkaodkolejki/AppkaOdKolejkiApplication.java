package com.example.appkaodkolejki;

import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppkaOdKolejkiApplication {

	public static void main(String[] args) {
		OpenCV.loadLocally();

		SpringApplication.run(AppkaOdKolejkiApplication.class, args);
	}

}
