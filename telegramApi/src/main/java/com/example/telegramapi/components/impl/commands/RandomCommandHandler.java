package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.components.additions.RandomMessageSender;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RandomCommandHandler extends RequestHandler  {
    private static final String command = "/random";

    private final RandomMessageSender randomMessageSender;

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        randomMessageSender.sendMessage(request);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
