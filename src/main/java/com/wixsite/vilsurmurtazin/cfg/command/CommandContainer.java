package com.wixsite.vilsurmurtazin.cfg.command;


import com.wixsite.vilsurmurtazin.cfg.service.FindNewPostsService;
import com.wixsite.vilsurmurtazin.cfg.service.SendBotMessageService;
import com.wixsite.vilsurmurtazin.cfg.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;


/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;


    public CommandContainer(SendBotMessageService sendBotMessageService,
                            TelegramUserService telegramUserService,
                            FindNewPostsService findNewPostsService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(CommandName.START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService,
                        findNewPostsService))
                .put(CommandName.STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(CommandName.HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(CommandName.NO.getCommandName(), new NoCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command findCommand(String commandIdentifier, String username) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}