package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.components.sup.test.TestComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LearnCommandHandler extends RequestHandler {
    private final TestComponent testComponent;

    private static final String command = "/test";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        testComponent.handleTestRequest(request);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
