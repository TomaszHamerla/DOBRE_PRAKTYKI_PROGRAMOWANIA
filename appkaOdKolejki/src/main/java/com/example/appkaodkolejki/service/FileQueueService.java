package com.example.appkaodkolejki.service;

import com.example.appkaodkolejki.model.JobStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileQueueService {

    private static final String FILE_NAME = "queue.csv";

    public synchronized void addJob(String jobId) throws IOException {
        String line = jobId + "," + JobStatus.PENDING + "\n";
        Files.write(Paths.get(FILE_NAME), line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        System.out.println("Producer: Dodano zadanie " + jobId);
    }

    public synchronized String claimNextJob() throws IOException {
        Path path = Paths.get(FILE_NAME);
        if (!Files.exists(path)) return null;

        List<String> lines = Files.readAllLines(path);
        List<String> updatedLines = new ArrayList<>();
        String claimedJobId = null;

        for (String line : lines) {
            String[] parts = line.split(",");

            if (claimedJobId == null && parts[1].equals(JobStatus.PENDING.name())) {
                claimedJobId = parts[0];
                updatedLines.add(parts[0] + "," + JobStatus.IN_PROGRESS);
            } else {
                updatedLines.add(line);
            }
        }

        if (claimedJobId != null) {
            Files.write(path, updatedLines);
        }
        return claimedJobId;
    }

    public synchronized void markJobAsDone(String jobId) throws IOException {
        Path path = Paths.get(FILE_NAME);
        List<String> lines = Files.readAllLines(path);
        List<String> updatedLines = lines.stream()
                .map(line -> {
                    String[] parts = line.split(",");
                    if (parts[0].equals(jobId)) {
                        return parts[0] + "," + JobStatus.DONE;
                    }
                    return line;
                })
                .collect(Collectors.toList());
        Files.write(path, updatedLines);
    }
}
