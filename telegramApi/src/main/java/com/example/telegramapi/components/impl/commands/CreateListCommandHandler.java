package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.components.additions.LangBeforeListComponent;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateListCommandHandler extends RequestHandler {
    private static final String command = "/newlist";

    private final LangBeforeListComponent langBeforeListComponent;

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        langBeforeListComponent.requireLang(request);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
