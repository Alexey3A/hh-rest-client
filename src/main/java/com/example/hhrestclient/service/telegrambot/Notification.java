package com.example.hhrestclient.service.telegrambot;

import com.example.hhrestclient.JobClient;
import com.example.hhrestclient.entity.Job;
import com.example.hhrestclient.service.jobsearch.ParseWithJsoup;
import com.example.hhrestclient.service.jobsearch.ParserJob;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Set;

@Getter
@Setter
public class Notification extends TelegramLongPollingBot {
    @Autowired
    private RestTemplate restTemplate;
    static final String URL = "http://localhost:8080/api/vacancies";
    @Autowired
    JobClient jobClient;
/*    @Autowired
    private ParserJob parserJob;*/
    @Autowired
    private ParseWithJsoup parseWithJsoup;
    @Value("${bot.adminId}")
    private long adminId;
    private static boolean condition = false;

    @Autowired
    public Notification(String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        User user = null;
        Message msg = null;

        if (update.hasMessage() && update.getMessage().hasText()) {
            msg = update.getMessage();
            user = msg.getFrom();
        }

        assert user != null;
        long tgUserID = user.getId();

        if (msg.isCommand()) {
            var txt = msg.getText();
            if (txt.equals("/start")) {
                Thread thread = new Thread(() -> {
                    condition = true;
                    sendText(tgUserID, "Начался поиск подходящих вакансий");
                    while (condition) {
                        Set<Job> jobSet = jobClient.getAllJob();
//                        Set<Job> newJobSet = parserJob.parse();
                        Set<Job> newJobSet = parseWithJsoup.parse();
                        for (Job job : newJobSet) {
                            if (!jobSet.contains(job)) {
                                System.out.println(job);
                                restTemplate.postForEntity(URL, job, job.getClass());
                                sendText(tgUserID, "Найдена новая работа: " + job);
                            }
                        }
                        try {
                            Thread.sleep(60000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("--------------");
                    }
                });
                thread.start();
            }
            if (txt.equals("/stop")) {
                condition = false;
                sendText(tgUserID, "Поиск вакансий остановлен");
            }
        }
    }

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what)
                .build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "hh_reaction_bot";
    }
}
