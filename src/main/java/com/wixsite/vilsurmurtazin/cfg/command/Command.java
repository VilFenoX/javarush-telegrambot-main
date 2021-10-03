package com.wixsite.vilsurmurtazin.cfg.command;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public interface Command {
    void execute(Update update) throws IOException;
}
