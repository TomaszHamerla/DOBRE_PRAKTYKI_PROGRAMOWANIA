package com.example.appkaodkolejki;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@RequiredArgsConstructor
public class AppStartupConfig {

    private final ConsumerWorker consumerWorker;

    @EventListener(ApplicationReadyEvent.class)
    public void startConsumers() {
        consumerWorker.startConsumerLoop("Consumer-1");
        consumerWorker.startConsumerLoop("Consumer-2");
        consumerWorker.startConsumerLoop("Consumer-3");
    }

}
