package com.example.hhrestclient;

import com.example.hhrestclient.entity.Job;
import com.example.hhrestclient.entity.Vacancies;
import com.example.hhrestclient.service.Communication;
import com.example.hhrestclient.service.jobsearch.ParserJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class JobClient {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    Communication communication;

    static final String URL = "http://localhost:8080/api/vacancies";

    public Set<Job> getAllJob() {
        ResponseEntity<Set<Job>> responseEntity = restTemplate.exchange(URL
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<Set<Job>>() {
                });
        return responseEntity.getBody();
    }


    //        @PostConstruct
    public void monitoringOfVacancies() {

        Set<Job> jobSet = getAllJob();
        Set<Job> newJobSet = new HashSet<>();

/*        Job job = new Job("123"
                , "new name"
                , "OOO OOO"
                , "Moscow"
                , 574635847338L
                , "www.spb.ru");

        ResponseEntity<? extends Job> responseEntity = restTemplate.postForEntity(URL, job, job.getClass());
        System.out.println(responseEntity.getBody());*/

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
    }

    public List<Job> saveVacancies() {
        List<Job> jobList = new ArrayList<>();
        communication.getAllVacancies().getItems().forEach(hhVacancy -> {
            Job job = new Job();
            job.setName(hhVacancy.getName());
            job.setCompany(hhVacancy.getDepartment().getName());
            job.setHref(hhVacancy.getAlternate_url());
            job.setCity(hhVacancy.getArea().getName());
            restTemplate.postForEntity(URL, job, job.getClass());
            jobList.add(job);
        });
        return jobList;
    }
}
