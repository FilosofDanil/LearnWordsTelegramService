package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.entities.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignmentsQuery implements QueryHandler {
    @Override
    public void handle(UserRequest request) {

    }

    @Override
    public String getCallbackQuery(String lang) {
        if (lang.equals("en")) {
            return "🎓 My Assignments";
        } else {
            return "🎓 Мої завдання";
        }
    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
