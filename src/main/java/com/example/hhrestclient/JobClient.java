package com.example.hhrestclient;

import com.example.hhrestclient.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import jakarta.annotation.PostConstruct;

import java.util.HashSet;
import java.util.Set;

@Component
public class JobClient {
    @Autowired
    private RestTemplate restTemplate;

    static final String URL = "http://localhost:8080/api/vacancies";

    public Set<Job> getAllJob() {
        ResponseEntity<Set<Job>> responseEntity = restTemplate.exchange(URL
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<Set<Job>>() {
                });
        return responseEntity.getBody();
    }


//    @PostConstruct
    public void monitoringOfVacancies() {

        Set<Job> jobSet = getAllJob();
        Set<Job> newJobSet = new HashSet<>();

        while (true){
            for (Job job : jobSet) {
                if (!newJobSet.contains(job)) {
                    newJobSet.add(job);
                    System.out.println(job);

                    restTemplate.postForEntity(URL, job, job.getClass());

                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            jobSet = getAllJob();
        }
    }

}
