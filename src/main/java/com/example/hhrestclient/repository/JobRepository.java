package com.example.hhrestclient.repository;

import com.example.hhrestclient.entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
}
