package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TelegramBotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StartCommandHandler extends RequestHandler {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private static final String command = "/start";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        UserSession session = request.getUserSession();
        session.setState(States.CONVERSATION_STARTED);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(),
                "Hello! We're glad that you're prefer our service. For a further usage you just follow to the menu, by the /menu command.\n" +
                        "If you have some questions, you could click /help and read any information about this bot, and also you can get instruction\n" +
                        "there, but we're sure, that in our simple service it'll be unnecessary. If you would like to rate it or write something just click \n" +
                        "/rate and write down your questions or regards. Thank you and enjoy it :) \n ");
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
