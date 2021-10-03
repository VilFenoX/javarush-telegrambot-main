package com.wixsite.vilsurmurtazin.cfg.command;

import com.wixsite.vilsurmurtazin.cfg.repository.entity.TelegramUser;
import com.wixsite.vilsurmurtazin.cfg.service.FindNewPostsService;
import com.wixsite.vilsurmurtazin.cfg.service.SendBotMessageService;
import com.wixsite.vilsurmurtazin.cfg.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

/**
 * Start {@link Command}.
 */
public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final FindNewPostsService findNewPostsService;

    public final static String START_MESSAGE = "Привет. Я CheckFreeGames Telegram Bot.\n " +
            "Я помогу тебе быть в курсе последних новостей, связанных с бесплатной раздачей игр в популярных" +
            " онлайн магазинах Steam, GOG, Epic Games Store  и др.\n\n" +
            "Не знаешь о чем я? Напиши /help, чтобы узнать что я умею.";

    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                        FindNewPostsService findNewPostsService ) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.findNewPostsService=findNewPostsService;
    }

    @Override
    public void execute(Update update) throws IOException {
        Long chatId = CommandUtils.getChatId(update);

        telegramUserService.findByChatId(chatId).ifPresentOrElse(
                user -> {
                    user.setActive(true);
                    telegramUserService.save(user);
                },
                () -> {
                    TelegramUser telegramUser = new TelegramUser();
                    telegramUser.setActive(true);
                    telegramUser.setChatId(chatId);
                    telegramUserService.save(telegramUser);
                });

        sendBotMessageService.sendMessage(chatId, START_MESSAGE);
    }
}
