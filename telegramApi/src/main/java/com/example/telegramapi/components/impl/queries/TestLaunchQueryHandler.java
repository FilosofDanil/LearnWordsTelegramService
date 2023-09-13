package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.components.additions.TestLaunchComponent;
import com.example.telegramapi.entities.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestLaunchQueryHandler implements QueryHandler {
    private final TestLaunchComponent testLaunchComponent;

    @Override
    public void handle(UserRequest request) {
        testLaunchComponent.launchPickedTest(request);
    }

    @Override
    public String getCallbackQuery(String lang) {
        if (lang.equals("en")) {
            return "🚀 Launch the test.";
        } else {
            return "🚀 Запустити цей тест.";
        }
    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
