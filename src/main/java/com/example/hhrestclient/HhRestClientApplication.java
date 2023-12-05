package com.example.hhrestclient;

import com.example.hhrestclient.service.telegrambot.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class HhRestClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(HhRestClientApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	public Notification getNotification(@Value("${botToken}") String botToken){
		return new Notification(botToken);
	}
}
