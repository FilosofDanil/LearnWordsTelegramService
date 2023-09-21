package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.components.sup.test.AssignmentComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignmentCommandHandler extends RequestHandler {
    private static final String command = "/assignments";

    private final AssignmentComponent assignmentComponent;

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        assignmentComponent.handle(request);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
