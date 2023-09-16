package com.example.telegramapi.services.scheduled;

import com.example.telegramapi.components.sup.test.FormTestComponent;
import com.example.telegramapi.services.MongoDBService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TestService;
import com.example.telegramapi.services.UserService;
import com.example.telegramapi.threads.GenerateTestThread;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class TestAutoGenerator {
    private final FormTestComponent testComponent;

    private final TestService testService;

    private final MongoDBService mongoDBService;

    private final SessionService sessionService;

    private final UserService userService;

    @Scheduled(cron = "* * * * * ?")
    public void generateTests() throws InterruptedException {
        GenerateTestThread testThread = new GenerateTestThread(testComponent, testService, mongoDBService, sessionService, userService);
        testThread.start();
        synchronized (testThread) {
            testThread.wait();
        }
    }


}
