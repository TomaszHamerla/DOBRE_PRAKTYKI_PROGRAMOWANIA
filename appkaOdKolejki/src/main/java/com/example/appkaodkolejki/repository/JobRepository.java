package com.example.appkaodkolejki.repository;

import com.example.appkaodkolejki.model.Job;
import com.example.appkaodkolejki.model.JobStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Job> findFirstByStatus(JobStatus status);
}
