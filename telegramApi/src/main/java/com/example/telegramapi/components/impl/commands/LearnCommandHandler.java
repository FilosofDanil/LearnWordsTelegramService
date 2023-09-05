package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.components.additions.FormTestComponent;
import com.example.telegramapi.components.additions.TestTabComponent;
import com.example.telegramapi.entities.*;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.MongoDBService;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TestService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.sorterts.TestEntitiesDateComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LearnCommandHandler extends RequestHandler {

    private final TestTabComponent testTabComponent;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final TestService testService;

    private final FormTestComponent formComponent;

    private final MongoDBService mongoDBService;

    private static final String command = "/test";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session = sessionService.checkUseData(session, request);
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        List<TestEntity> tests = testService.getAllByUserId(session.getUserData().getUser().getId());
        if (!tests.isEmpty()) {
            TestEntity first = getFirst(tests);
            if (first.getTestDate().before(new Date())) {
                session.setState(States.PREPARES_TEST);
                sessionService.saveSession(request.getChatId(), session);
                telegramService.sendMessage(request.getChatId(), obtainTextService.read(
                        "PreparingTest", lang));
                createTest(first, lang);
                startTest(session, first);
                sessionService.saveSession(request.getChatId(), session);
                telegramService.sendMessage(request.getChatId(), formTaskString(session));
            }
        } else {
            testTabComponent.handleTestRequest(request);
        }
    }

    private TestEntity getFirst(List<TestEntity> tests) {
        tests.sort(new TestEntitiesDateComparator());
        return tests.get(0);
    }

    private void createTest(TestEntity first, String lang) {
        List<Test> resultTests = formComponent.formTest(first, lang, mongoDBService.getById(first.getListId()));
        first.setTests(resultTests);
        testService.update(first, first.getId());
    }

    private void startTest(UserSession session, TestEntity first) {
        UserData userData = session.getUserData();
        userData.setCurrentTest(first);
        userData.setCurrentTask(0);
        session.setUserData(userData);
        session.setState(States.TEST_STARTED);
    }

    private String formTaskString(UserSession session) {
        Test firstTask = session.getUserData().getCurrentTest().getTests().get(0);
        String responseMessage = "Test Started! \n " + "Task#0 \n";
        return responseMessage + firstTask.getResponseMessage();
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
