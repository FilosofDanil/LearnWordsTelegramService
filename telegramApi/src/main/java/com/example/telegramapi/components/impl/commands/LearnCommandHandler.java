package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.components.additions.FormTestComponent;
import com.example.telegramapi.components.additions.TestTabComponent;
import com.example.telegramapi.entities.Test;
import com.example.telegramapi.entities.TestEntity;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.MongoDBService;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TestService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.services.impl.TranslationServiceBean;
import com.example.telegramapi.sorterts.TestEntitiesDateComparator;
import jakarta.xml.bind.SchemaOutputResolver;
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
            tests.sort(new TestEntitiesDateComparator());
            TestEntity first = tests.get(0);
            if (first.getTestDate().before(new Date())) {
                session.setState(States.PREPARES_TEST);
                sessionService.saveSession(request.getChatId(), session);
                telegramService.sendMessage(request.getChatId(), obtainTextService.read(
                        "PreparingTest", lang));
                List<Test> resultTests = formComponent.formTest(first, lang, mongoDBService.getById(first.getListId()));
                first.setTests(resultTests);
                testService.update(first, first.getId());
            }
        } else {
            testTabComponent.handleTestRequest(request);
        }
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
