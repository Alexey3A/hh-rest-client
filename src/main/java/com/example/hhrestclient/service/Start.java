package com.example.hhrestclient.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class Start {
    @Autowired
    Communication communication;

    @PostConstruct
    public void start(){
        System.out.println(communication.getAllVacancies());
    }
}
