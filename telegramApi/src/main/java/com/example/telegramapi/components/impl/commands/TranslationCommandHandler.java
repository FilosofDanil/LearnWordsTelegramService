package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.components.sup.translation.TranslationComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TranslationCommandHandler extends RequestHandler {
    private static final String command = "/translate";

    private final TranslationComponent translationComponent;

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        translationComponent.handleTranslationRequest(request);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
