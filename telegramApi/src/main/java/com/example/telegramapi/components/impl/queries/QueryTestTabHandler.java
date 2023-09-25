package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.components.sup.tab.TestTabComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        if (lang.equals("en")) return "✍🏻 Start Learning";
        else return "✍🏻 Почати навчання";

    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
