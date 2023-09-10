package com.example.telegramapi.components.additions;

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
public class TestLaunchComponent {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final TestService testService;

    private final FormTestComponent formComponent;

    private final MongoDBService mongoDBService;

    public void launchPickedTest(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        TestEntity test = getTest(session);
        if (test != null) {
            if (test.getTestDate().before(new Date())) {
                session.setState(States.PREPARES_TEST);
                sessionService.saveSession(request.getChatId(), session);
                telegramService.sendMessage(request.getChatId(), obtainTextService.read(
                        "PreparingTest", lang));
                createTest(test, lang);
                startTest(session, test);
                sessionService.saveSession(request.getChatId(), session);
                telegramService.sendMessage(request.getChatId(), formTaskString(session));
            }
        } else {
            telegramService.sendMessage(request.getChatId(), "Expired!");
        }

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

    private void createTest(TestEntity first, String lang) {
        List<Test> resultTests = formComponent.formTest(first, lang, mongoDBService.getById(first.getListId()));
        first.setTests(resultTests);
        testService.update(first, first.getId());
    }

    private TestEntity getTest(UserSession session) {
        return testService.getByWordListId(session.getUserData().getPreviousMessage());
    }
}
