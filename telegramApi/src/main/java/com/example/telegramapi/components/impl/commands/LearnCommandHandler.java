package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.components.additions.TestTabComponent;
import com.example.telegramapi.entities.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LearnCommandHandler extends RequestHandler {

    private final TestTabComponent testTabComponent;

    private static final String command = "/test";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        testTabComponent.handleTestRequest(request);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
