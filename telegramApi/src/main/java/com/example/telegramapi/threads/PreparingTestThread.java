package com.example.telegramapi.threads;

import com.example.telegramapi.services.TestService;
import feign.FeignException;
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
            try{
                while (!testService.getById(testId).getTestReady()) Thread.sleep(1000);
                notify();
            } catch (FeignException ex){
                System.out.println("Connection lost!");
            }
        }
    }
}
