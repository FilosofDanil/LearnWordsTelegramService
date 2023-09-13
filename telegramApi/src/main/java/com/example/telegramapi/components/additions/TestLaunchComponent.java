package com.example.telegramapi.components.additions;

import com.example.telegramapi.entities.*;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TestService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.threads.PreparingTestThread;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TestLaunchComponent {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final PreparingTestComponent preparingTestComponent;

    private final TestService testService;

    public void launchPickedTest(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        TestEntity test = getTest(session);
        if (test != null) {
            if (test.getTestDate().before(new Date())) {
                if (!test.getTestReady()) {
                    preparingTestComponent.prepareTest(request);
                    PreparingTestThread testThread = new PreparingTestThread(test.getId(), testService);
                    synchronized (testThread) {
                        try {
                            testThread.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    test = testService.getById(test.getId());
                }
                startTest(session, test);
                sessionService.saveSession(request.getChatId(), session);
                telegramService.sendMessage(request.getChatId(), formTaskString(session));
            }
        } else {
            telegramService.sendMessage(request.getChatId(), "Test not ready or it's expired");
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

    //    private void createTest(TestEntity first, String lang) {
//        List<Test> resultTests = formComponent.formTest(first, lang, mongoDBService.getById(first.getListId()));
//        first.setTests(resultTests);
//        testService.update(first, first.getId());
//    }
//
    private TestEntity getTest(UserSession session) {
        return testService.getByWordListId(session.getUserData().getPreviousMessage());
    }
}
