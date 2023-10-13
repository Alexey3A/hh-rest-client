package com.example.hhrestclient.service;

import com.example.hhrestclient.entity.Job;
import com.example.hhrestclient.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobRepositoryService {
    @Autowired
    JobRepository jobRepository;

    Job saveJob(Job job){
        return jobRepository.save(job);
    }
}
