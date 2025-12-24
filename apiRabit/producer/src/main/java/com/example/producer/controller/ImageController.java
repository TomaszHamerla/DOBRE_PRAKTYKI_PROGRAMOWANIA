package com.example.producer.controller;

import com.example.producer.model.ImageTask;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.UUID;

@RestController
public class ImageController {

    private final RabbitTemplate rabbitTemplate;
    private final String RESULTS_DIR = "results/";

    public ImageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/analyze_img")
    public String analyze(@RequestParam String url) {
        String id = UUID.randomUUID().toString();
        rabbitTemplate.convertAndSend("image-queue", new ImageTask(id, url));
        return "Przyjęto zadanie ID: " + id;
    }

    @GetMapping("/check")
    public String check(@RequestParam String id) {
        File folder = new File(RESULTS_DIR);

        if (folder.exists()) {
            File[] files = folder.listFiles((d, n) -> n.startsWith(id));

            if (files != null && files.length > 0) {
                String foundFileName = files[0].getName();

                try {
                    String countPart = foundFileName.substring(foundFileName.lastIndexOf('-') + 1, foundFileName.lastIndexOf('.'));
                    return "Status: UKOŃCZONO. Wykryto osób: " + countPart;
                } catch (Exception e) {
                    return "Status: UKOŃCZONO. (Plik: " + foundFileName + ")";
                }
            }
        }
        return "Status: PRZETWARZANIE... (Zadanie w kolejce lub w trakcie analizy)";
    }
}
