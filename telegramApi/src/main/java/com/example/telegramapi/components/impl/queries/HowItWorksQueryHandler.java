package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.components.sup.intermidiate_operations.SendHelpMessage;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HowItWorksQueryHandler implements QueryHandler {

    private final SessionService sessionService;

    private final SendHelpMessage sendHelpMessage;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        sendHelpMessage.sendHelpMessage(request.getChatId(), "how_it_works", lang);
    }

    @Override
    public String getCallbackQuery(String lang) {
        if (lang.equals("en")) return "How to pass tests?";
        else return "Як працює бот?";
    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
