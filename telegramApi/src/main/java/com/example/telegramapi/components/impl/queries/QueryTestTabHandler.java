package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.components.additions.TestTabComponent;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QueryTestTabHandler implements QueryHandler {
    private final TestTabComponent testTabComponent;

    @Override
    public void handle(UserRequest request) {
        testTabComponent.handleTestRequest(request);
    }

    @Override
    public String getCallbackQuery(String lang) {
        if (lang.equals("en")) {
            return "✍🏻 Start Learning";
        } else {
            return "✍🏻 Почати навчання";
        }
    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
