package com.example.telegramapi.components.impl.texts;

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
        session = sessionService.checkUseData(session, request);
        TestEntity test = session.getUserData().getCurrentTest();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        if (test != null) {
            if (session.getUserData().getCurrentTask() + 1 < test.getTests().size()) {
                int taskNum = nextTest(session);
                Test current = session.getUserData().getCurrentTest().getTests().get(taskNum);
                if(current.getTestFormat().equals(TestFormat.PICK_FROM_LIST_FORMAT)){
                    telegramService.sendMessage(request.getChatId(), formTaskString(session), ReplyKeyboardHelper.buildMainMenu(current.getResponseKeyboard()));
                    return;
                }
                telegramService.sendMessage(request.getChatId(), formTaskString(session));
            } else {
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

    private void saveTestInUserData() {

    }

    private void saveTest(UserSession session, TestEntity test) {
        UserData userData = session.getUserData();
        userData.setCurrentTest(null);
        userData.setCurrentTest(null);
        session.setUserData(userData);
        testService.update(test, test.getId());
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
