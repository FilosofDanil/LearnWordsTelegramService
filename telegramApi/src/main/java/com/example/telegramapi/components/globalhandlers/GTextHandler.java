package com.example.telegramapi.components.globalhandlers;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.services.SessionService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GTextHandler extends RequestHandler {
    private final List<TextHandler> textHandlers;

    private final SessionService sessionService;

    @Override
    public boolean isApplicable(UserRequest request) {
        return isTextMessage(request.getUpdate());
    }


    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        try {
            for (TextHandler textHandler : textHandlers) {
                if (textHandler.getApplicableState().equals(session.getState())) {
                    textHandler.handle(request);
                    return;
                }
            }
        } catch (FeignException ex) {
            if (ex.status() == 400) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
