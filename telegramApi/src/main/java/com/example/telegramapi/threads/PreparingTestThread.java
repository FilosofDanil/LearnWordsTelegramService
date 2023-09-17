package com.example.telegramapi.threads;

import com.example.telegramapi.services.TestService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class PreparingTestThread extends Thread {

    private String testId;

    private final TestService testService;

    @SneakyThrows
    @Override
    public void run() {
        synchronized (this){
            while (!testService.getById(testId).getTestReady()) Thread.sleep(1000);
            notify();
        }
    }
}
