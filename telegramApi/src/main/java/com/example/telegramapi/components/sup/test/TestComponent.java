package com.example.telegramapi.components.sup.test;

import com.example.telegramapi.components.sup.intermidiate_operations.PreparingTestComponent;
import com.example.telegramapi.components.sup.tab.TestTabComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.tests_data.Test;
import com.example.telegramapi.entities.tests_data.TestEntity;
import com.example.telegramapi.entities.user.UserData;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.enums.TestFormat;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TestService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.sorterts.TestEntitiesDateComparator;
import com.example.telegramapi.threads.PreparingTestThread;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestComponent {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final PreparingTestComponent preparingTestComponent;

    private final TestService testService;

    private final TestTabComponent testTabComponent;

    public void handleTestRequest(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        List<TestEntity> tests = testService.getAllByUserId(session.getUserData().getUser().getId());
        if (!tests.isEmpty()) {
            TestEntity nearestTest = getTest(session, tests);
            if (nearestTest.getTestDate().before(new Date())) {
                if (!nearestTest.getTestReady()) {
                    preparingTestComponent.prepareTest(request);
                    PreparingTestThread testThread = new PreparingTestThread(nearestTest.getId(), testService);
                    synchronized (testThread) {
                        try {
                            testThread.wait(180000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    nearestTest = testService.getById(nearestTest.getId());
                }
                startTest(session, nearestTest);
                sessionService.saveSession(request.getChatId(), session);
                telegramService.sendMessage(request.getChatId(), formTaskString(session));
            } else testTabComponent.handleTestRequest(request);
        } else testTabComponent.handleTestRequest(request);
        sessionService.saveSession(request.getChatId(), session);
    }

    private void startTest(UserSession session, TestEntity nearestTest) {
        UserData userData = session.getUserData();
        userData.setCurrentTest(nearestTest);
        userData.setCurrentTask(0);
        session.setUserData(userData);
        if (nearestTest.getTests().get(0).getTestFormat().equals(TestFormat.QUIZ_FORMAT)) session.setState(States.QUIZ);
        else session.setState(States.TEST_STARTED);
    }

    private String formTaskString(UserSession session) {
        Test nearestTestTask = session.getUserData().getCurrentTest().getTests().get(0);
        String responseMessage = "Test Started! \n " + "Task#0 \n";
        return responseMessage + nearestTestTask.getResponseMessage();
    }

    private TestEntity getTest(UserSession session, List<TestEntity> tests) {
        if (session.getUserData().getPreviousMessage() == null) {
            tests.sort(new TestEntitiesDateComparator());
            return tests.get(0);
        } else {
            TestEntity nearestTest = testService.getByWordListId(session.getUserData().getPreviousMessage());
            if (nearestTest.getTestDate().before(new Date())) {
                tests.sort(new TestEntitiesDateComparator());
                return tests.get(0);
            }
            return nearestTest;
        }
    }
}
