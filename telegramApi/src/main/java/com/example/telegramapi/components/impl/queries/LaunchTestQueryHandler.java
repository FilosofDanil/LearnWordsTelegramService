package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.components.sup.test.TestComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LaunchTestQueryHandler implements QueryHandler {
    private final TestComponent testComponent;

    @Override
    public void handle(UserRequest request) {
        testComponent.handleTestRequest(request);
    }

    @Override
    public String getCallbackQuery(String lang) {
        if (lang.equals("en")) return "🚀 Launch Test";
        else return "🚀 Запустити Тест";

    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
