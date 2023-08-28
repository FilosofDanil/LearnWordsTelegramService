package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.entities.User;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TelegramBotService;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class StartCommandHandler extends RequestHandler {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final UserService userService;

    private static final String command = "/start";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        String username = request.getUpdate().getMessage().getChat().getUserName();
        String firstName = request.getUpdate().getMessage().getChat().getFirstName();
        if (userService.getByUsername(username) == null) {
            userService.create(User.builder()
                    .username(username)
                    .registrationDate(new Date())
                    .tgname(firstName)
                    .build());
        }
        UserSession session = request.getUserSession();
        session.setState(States.CONVERSATION_STARTED);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(),
                obtainTextService.read("Start", "uk"));
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
