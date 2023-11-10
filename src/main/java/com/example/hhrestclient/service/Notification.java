package com.example.hhrestclient.service;

import com.example.hhrestclient.ParserJob;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Getter
@Setter
public class Notification extends TelegramLongPollingBot {
    @Autowired
    private ParserJob parserJob;
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
//                parserJob.parse();
                    while (condition) {
                        System.out.println("hello");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
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
