package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.entities.TestEntity;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TestService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.sorterts.TestEntitiesDateComparator;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AssignmentsQuery implements QueryHandler {
    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final TelegramBotService telegramService;

    private final TestService testService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session.setState(States.ASSIGNMENTS);
        sessionService.saveSession(request.getChatId(), session);
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        List<TestEntity> tests = testService.getAllByUserId(session.getUserData().getUser().getId());
        if (!tests.isEmpty()) {
            TestEntity first = getFirst(tests);
            if (first.getTestDate().before(new Date())) {
                telegramService.sendMessage(request.getChatId(), obtainTextService.read("assignments", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
            } else {
                telegramService.sendMessage(request.getChatId(), obtainTextService.read("emptyAssignments", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
            }
        } else {
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("emptyAssignments", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
        }
    }

    private void sendAssignments() {

    }

    private TestEntity getFirst(List<TestEntity> tests) {
        tests.sort(new TestEntitiesDateComparator());
        return tests.get(0);
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
