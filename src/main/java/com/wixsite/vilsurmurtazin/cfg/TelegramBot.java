package com.wixsite.vilsurmurtazin.cfg;

import com.wixsite.vilsurmurtazin.cfg.command.CommandContainer;
import com.wixsite.vilsurmurtazin.cfg.service.FindNewPostsService;
import com.wixsite.vilsurmurtazin.cfg.service.SendBotMessageServiceImpl;
import com.wixsite.vilsurmurtazin.cfg.service.TelegramUserService;
import com.wixsite.vilsurmurtazin.cfg.command.CommandName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

/**
 * Telegram bot from  Check Free Games from Steam, GOG, Epic Games Store ...
 */
@Component
public class TelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;


    @Autowired
    public TelegramBot(TelegramUserService telegramUserService, @Lazy FindNewPostsService findNewPostsService) {
        this.commandContainer =
                new CommandContainer(new SendBotMessageServiceImpl(this), telegramUserService, findNewPostsService);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String username = update.getMessage().getFrom().getUserName();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                try {
                    commandContainer.findCommand(commandIdentifier, username).execute(update);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    commandContainer.findCommand(CommandName.NO.getCommandName(), username).execute(update);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
