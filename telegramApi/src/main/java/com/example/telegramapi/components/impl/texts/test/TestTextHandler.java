package com.example.telegramapi.components.impl.texts.test;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.*;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.enums.TestFormat;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TestService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class TestTextHandler implements TextHandler {
    private final States applicable = States.TEST_STARTED;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final TestService testService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        TestEntity test = session.getUserData().getCurrentTest();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        int curr = session.getUserData().getCurrentTask();
        if (test != null) {
            if (curr + 1 < test.getTests().size()) {
                checkPreviousTest(test, request.getUpdate().getMessage().getText(), session);
                saveInUserData(test, session);
                sessionService.saveSession(request.getChatId(), session);
                if (test.getTests().get(curr).getCorrect()) {
                    telegramService.sendMessage(request.getChatId(), obtainTextService.read("correct", lang));
                } else {
                    telegramService.sendMessage(request.getChatId(), obtainTextService.read("incorrect", lang));
                }
                curr = nextTest(session);
                Test current = session.getUserData().getCurrentTest().getTests().get(curr);
                if (current.getTestFormat().equals(TestFormat.PICK_FROM_LIST_FORMAT)) {
                    telegramService.sendMessage(request.getChatId(), formTaskString(session), ReplyKeyboardHelper.buildMainMenu(current.getResponseKeyboard()));
                    return;
                }
                if (current.getTestFormat().equals(TestFormat.QUIZ_FORMAT)) {
                    session.setState(States.QUIZ);
                    sessionService.saveSession(request.getChatId(), session);
                }
                telegramService.sendMessage(request.getChatId(), formTaskString(session));
            } else {
                test.setPassedTimes(test.getPassedTimes() + 1);
                saveTest(session, test);
                session.setState(States.TEST_FINISHED);
                sessionService.saveSession(request.getChatId(), session);
                telegramService.sendMessage(request.getChatId(), obtainTextService.read("endTest", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
            }

        }
    }

    private int nextTest(UserSession session) {
        UserData userData = session.getUserData();
        int taskNum = userData.getCurrentTask() + 1;
        userData.setCurrentTask(taskNum);
        session.setUserData(userData);
        return taskNum;
    }

    private String formTaskString(UserSession session) {
        int taskNum = session.getUserData().getCurrentTask();
        Test firstTask = session.getUserData().getCurrentTest().getTests().get(taskNum);
        String responseMessage = "Task#" + taskNum + " \n";
        return responseMessage + firstTask.getResponseMessage();
    }

    private void saveTest(UserSession session, TestEntity test) {
        UserData userData = session.getUserData();
        userData.setCurrentTest(null);
        session.setUserData(userData);
        testService.update(test, test.getId());
    }

    private void checkPreviousTest(TestEntity test, String answer, UserSession session) {
        int curr = session.getUserData().getCurrentTask();
        boolean correct = checkAnswer(test, answer, curr);
        List<Test> tests = test.getTests();
        Test currTest = tests.get(curr);
        currTest.setCorrect(correct);
        tests.set(curr, currTest);
        test.setTests(tests);
    }

    private boolean checkAnswer(TestEntity test, String answer, int curr) {
        Test checkingTest = test.getTests().get(curr);
        if (checkingTest.getCorrectAnswers().contains(answer)) {
            return true;
        } else return checkingTest.getCorrectAnswers().contains(answer.toLowerCase(Locale.ROOT));
    }

    private void saveInUserData(TestEntity test, UserSession session) {
        UserData userData = session.getUserData();
        userData.setCurrentTest(test);
        session.setUserData(userData);
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
