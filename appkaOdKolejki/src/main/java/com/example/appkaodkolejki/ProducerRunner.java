package com.example.appkaodkolejki;

import com.example.appkaodkolejki.service.FileQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProducerRunner implements CommandLineRunner {

    private final FileQueueService fileQueueService;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 100; i++) {
            fileQueueService.addJob("ZADANIE_" + i);
        }
        System.out.println("--- PRODUCER ZAKOŃCZYŁ GENEROWANIE 100 ZADAŃ ---");
    }
}
