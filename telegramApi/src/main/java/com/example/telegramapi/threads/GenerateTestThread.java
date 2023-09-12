package com.example.telegramapi.threads;

import com.example.telegramapi.components.additions.FormTestComponent;
import com.example.telegramapi.entities.*;
import com.example.telegramapi.services.MongoDBService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TestService;
import com.example.telegramapi.services.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayDeque;
import java.util.List;


@AllArgsConstructor
public class GenerateTestThread extends Thread {
    private final FormTestComponent testComponent;

    private final TestService testService;

    private final MongoDBService mongoDBService;

    private final SessionService sessionService;

    private final UserService userService;

    @SneakyThrows
    @Override
    public void run() {
        List<TestEntity> tests = testService.getAll().stream().filter(testEntity -> !testEntity.getTestReady()).toList();
        ArrayDeque<TestEntity> testQueue = new ArrayDeque<>(tests);
        synchronized (this) {
            if (!testQueue.isEmpty()) {
                TestEntity first = testQueue.pollFirst();
                String wordListId = first.getListId();
                UserWordList wordList = mongoDBService.getById(wordListId);
                User user = userService.getById(wordList.getUserId());
                UserSession session = sessionService.getSession(user.getChatId());
                String lang = session.getUserData().getUserSettings().getInterfaceLang();
                createTest(first, lang);
            }
        }
    }

    private void createTest(TestEntity first, String lang) {
        List<Test> resultTests = testComponent.formTest(first, lang, mongoDBService.getById(first.getListId()));
        first.setTests(resultTests);
        first.setTestReady(true);
        testService.update(first, first.getId());
    }
}
