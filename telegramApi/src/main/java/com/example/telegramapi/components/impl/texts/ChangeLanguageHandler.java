package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeLanguageHandler implements TextHandler {
    private final States applicable = States.CHANGE_LANGUAGE;

    private final SessionService sessionService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session = sessionService.checkUseData(session, request);
        session.setState(States.CONVERSATION_STARTED);
        sessionService.saveSession(request.getChatId(), session);
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
