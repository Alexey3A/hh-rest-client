package com.example.hhrestclient.service;

import com.example.hhrestclient.entity.HHVacancy;
import com.example.hhrestclient.entity.Vacancies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class Communication {
    private final String URL = "https://api.hh.ru/vacancies?area=1&ored_clusters=true&search_period=1&text=Java+developer&order_by=publication_time";
    @Autowired
    RestTemplate restTemplate;

    public Vacancies getAllVacancies() {

        /*ResponseEntity<List<HHVacancy>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET
                , null, new ParameterizedTypeReference<List<HHVacancy>>() {
                }
        );
        List<HHVacancy> vacancies = responseEntity.getBody();*/

        Vacancies vacancies = restTemplate.getForEntity(URL, Vacancies.class).getBody();
        System.out.println(vacancies);
        return vacancies;
    }
}
