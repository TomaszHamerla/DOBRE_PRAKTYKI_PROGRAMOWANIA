package com.example.appkaodkolejki;

import com.example.appkaodkolejki.service.DbQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsumerWorker {

    private final DbQueueService dbQueueService;

    @Async
    public void startConsumerLoop(String consumerName) {
        System.out.println("Uruchomiono konsumenta: " + consumerName);

        while (true)
            try {
                String jobId = dbQueueService.claimNextJob();

                if (jobId != null) {
                    System.out.println(consumerName + ": Pobrał zadanie " + jobId + ". Praca...");

                    Thread.sleep(30000);

                    dbQueueService.markJobAsDone(jobId);
                    System.out.println(consumerName + ": Zakończył zadanie " + jobId + " (DONE)");
                } else {
                    System.out.println(consumerName + ": Brak zadań do przetworzenia. Odpoczynek...");
                    Thread.sleep(5000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
