package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.entities.tests_data.TestEntity;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.tests_data.UserWordList;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.MongoDBService;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TestService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.sorterts.TestEntitiesDateComparator;
import com.example.telegramapi.utils.InlineKeyboardHelper;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AssignmentsQuery implements QueryHandler {
    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final TelegramBotService telegramService;

    private final TestService testService;

    private final MongoDBService mongoDBService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session.setState(States.ASSIGNMENTS);
        sessionService.saveSession(request.getChatId(), session);
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        List<TestEntity> tests = testService.getAllByUserId(session.getUserData().getUser().getId());
        if (!tests.isEmpty()) {
            TestEntity first = getFirst(tests);
            if (first.getTestDate().before(new Date())) {
                telegramService.sendMessage(request.getChatId(), obtainTextService.read("assignments", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
                sendAssignments(tests, request);
            } else telegramService.sendMessage(request.getChatId(), obtainTextService.read("emptyAssignments", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
        } else telegramService.sendMessage(request.getChatId(), obtainTextService.read("emptyAssignments", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
    }

    private void sendAssignments(List<TestEntity> tests, UserRequest request) {
        List<TestEntity> filteredList = tests.stream().filter(testEntity -> testEntity.getTestDate().before(new Date())).toList();
        filteredList.forEach(testEntity -> {
            String backMessage = "Test#" + testEntity.getId() + "\n";
            UserWordList wordList = mongoDBService.getById(testEntity.getListId());
            if (wordList.getLangFrom().equals("en")) backMessage += "\uD83C\uDDEC\uD83C\uDDE7";
            else if (wordList.getLangFrom().equals("de")) backMessage += "\uD83C\uDDE9\uD83C\uDDEA ";
            else if (wordList.getLangFrom().equals("es")) backMessage += "\uD83C\uDDEA\uD83C\uDDF8 ";
            else if (wordList.getLangFrom().equals("fr")) backMessage += "\uD83C\uDDEB\uD83C\uDDF7 ";
            backMessage += testEntity.getTests().size() + " words \n";
            backMessage += "Deadline date: " + testEntity.getTestDate();
            telegramService.sendMessage(request.getChatId(), backMessage, InlineKeyboardHelper.buildInlineKeyboard(List.of("\uD83D\uDE80 Launch"), false));
        });

    }

    private TestEntity getFirst(List<TestEntity> tests) {
        tests.sort(new TestEntitiesDateComparator());
        return tests.get(0);
    }

    @Override
    public String getCallbackQuery(String lang) {
        if (lang.equals("en")) return "🎓 My Assignments";
        else return "🎓 Мої завдання";
    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
