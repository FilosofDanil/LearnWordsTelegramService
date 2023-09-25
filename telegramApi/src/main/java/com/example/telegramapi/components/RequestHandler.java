package com.example.telegramapi.components;

import com.example.telegramapi.entities.telegram.UserRequest;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class RequestHandler {

    public abstract boolean isApplicable(UserRequest request);

    public abstract void handle(UserRequest request);

    public abstract boolean isGlobal();

    public boolean isCommand(Update update, String command) {
        return update.hasMessage() && update.getMessage().isCommand()
                && update.getMessage().getText().equals(command);
    }

    public boolean isTextMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    public boolean isTextMessage(Update update, String text) {
        return update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals(text);
    }

    public boolean isBackQuery(Update update) {
        return update.hasCallbackQuery();
    }
}
