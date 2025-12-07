package com.example.appkaodkolejki.service;

import com.example.appkaodkolejki.model.Job;
import com.example.appkaodkolejki.model.JobStatus;
import com.example.appkaodkolejki.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DbQueueService {

    private final JobRepository jobRepository;

    public void addJob(String jobId) {
        Job job = new Job(jobId, JobStatus.PENDING);
        jobRepository.save(job);
        System.out.println("Producer: Dodano zadanie " + jobId);
    }

    @Transactional
    public String claimNextJob() {
        return jobRepository.findFirstByStatus(JobStatus.PENDING)
                .map(job -> {
                    job.setStatus(JobStatus.IN_PROGRESS);
                    return job.getId();
                })
                .orElse(null);
    }

    @Transactional
    public void markJobAsDone(String jobId) {
        jobRepository.findById(jobId).ifPresent(job -> {
            job.setStatus(JobStatus.DONE);
        });
    }
}
