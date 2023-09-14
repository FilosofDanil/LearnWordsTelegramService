package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.additions.QuizGenerateComponent;
import com.example.telegramapi.entities.*;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TestService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizTextHandler implements TextHandler {
    private final States applicable = States.QUIZ;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final TestService testService;

    private final QuizGenerateComponent quizGenerateComponent;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        TestEntity test = session.getUserData().getCurrentTest();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("quizFinished", lang));
        startTest(session, test);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), formTaskString(session));
    }

    private void startTest(UserSession session, TestEntity test) {
        UserData userData = session.getUserData();
        userData.setCurrentTest(test);
        userData.setCurrentTask(userData.getCurrentTask() + 1);
        session.setUserData(userData);
        session.setState(States.TEST_STARTED);
    }

    private String formTaskString(UserSession session) {
        Integer currentTask = session.getUserData().getCurrentTask();
        Test task = session.getUserData().getCurrentTest().getTests().get(currentTask);
        String responseMessage = "Test Started! \n " + "Task#" + currentTask + " \n";
        return responseMessage + task.getResponseMessage();
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
