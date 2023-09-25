package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.components.sup.test.AssignmentComponent;
import com.example.telegramapi.entities.tests_data.TestEntity;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.tests_data.UserWordList;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.MongoDBService;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TestService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.sorterts.TestEntitiesDateComparator;
import com.example.telegramapi.utils.InlineKeyboardHelper;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AssignmentsQueryHandler implements QueryHandler {
    private final AssignmentComponent assignmentComponent;

    @Override
    public void handle(UserRequest request) {
        assignmentComponent.handle(request);
    }

    @Override
    public String getCallbackQuery(String lang) {
        if (lang.equals("en")) return "🎓 My Assignments";
        else return "🎓 Мої завдання";
    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
