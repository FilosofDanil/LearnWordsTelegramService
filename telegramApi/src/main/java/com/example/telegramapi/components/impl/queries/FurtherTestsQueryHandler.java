package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.tests_data.TestEntity;
import com.example.telegramapi.entities.tests_data.UserWordList;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.MongoDBService;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TestService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FurtherTestsQueryHandler implements QueryHandler {
    private final TestService testService;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final MongoDBService mongoDBService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        session.setState(States.FURTHER_QUERY);
        sessionService.saveSession(request.getChatId(), session);
        List<TestEntity> tests = testService.getAllByUserId(session.getUserData().getUser().getId());
        tests = tests.stream().filter(test -> test.getTestDate().after(new Date())).toList();
        if (tests.isEmpty())
            telegramService.sendMessage(request.getChatId(), "Not yet!", ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
        StringBuilder response = new StringBuilder("You have some further tests: \n");
        tests.forEach(test -> {
            appendStringMessage(response, test);
        });
        telegramService.sendMessage(request.getChatId(), response.toString(), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
    }

    private void appendStringMessage(StringBuilder response, TestEntity test) {
        response.append("Test#" + test.getId() + "\n");
        UserWordList wordList = mongoDBService.getById(test.getListId());
        if (wordList.getLangFrom().equals("English")) response.append("\uD83C\uDDEC\uD83C\uDDE7");
        else if (wordList.getLangFrom().equals("German")) response.append("\uD83C\uDDE9\uD83C\uDDEA ");
        else if (wordList.getLangFrom().equals("Spanish")) response.append("\uD83C\uDDEA\uD83C\uDDF8 ");
        else if (wordList.getLangFrom().equals("French")) response.append("\uD83C\uDDEB\uD83C\uDDF7 ");
        response.append(test.getTests().size() + " words \n");
        response.append("Deadline date: " + test.getTestDate());
        response.append("\n");
    }

    @Override
    public String getCallbackQuery(String lang) {
        if (lang.equals("en")) return "👨🏻‍💻 Follow-Up-Tests";
        else return "👨🏻‍💻 Подальші тести";
    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
