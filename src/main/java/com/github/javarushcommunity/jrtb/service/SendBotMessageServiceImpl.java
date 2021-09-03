package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.JavaRushTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SendBotMessageServiceImpl implements SendBotMessageService {

    private  final JavaRushTelegramBot javaRushTelegramBot;

    @Autowired
    public SendBotMessageServiceImpl(JavaRushTelegramBot javaRushTelegramBot) {
        this.javaRushTelegramBot = javaRushTelegramBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            javaRushTelegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            //todo add logging to the project.
            e.printStackTrace();
        }
    }
}
