package com.example.telegramapi.components.additions;

import com.example.telegramapi.entities.*;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.MongoDBService;
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
public class TestComponent {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final TestService testService;

    private final FormTestComponent formComponent;

    private final MongoDBService mongoDBService;

    private final TestTabComponent testTabComponent;

    public void handleTestRequest(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
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
        sessionService.saveSession(request.getChatId(), session);

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

    private TestEntity getFirst(List<TestEntity> tests) {
        tests.sort(new TestEntitiesDateComparator());
        return tests.get(0);
    }

    private void createTest(TestEntity first, String lang) {
        List<Test> resultTests = formComponent.formTest(first, lang, mongoDBService.getById(first.getListId()));
        first.setTests(resultTests);
        testService.update(first, first.getId());
    }
}
