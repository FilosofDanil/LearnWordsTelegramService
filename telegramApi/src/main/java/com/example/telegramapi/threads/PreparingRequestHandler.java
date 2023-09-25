package com.example.telegramapi.threads;

import com.example.telegramapi.entities.gpt.GPTRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class PreparingRequestHandler extends Thread {
    private GPTRequest request;

    @SneakyThrows
    @Override
    public void run() {
        synchronized (this){
            while (!request.getReady()){
                Thread.sleep(1000);
            }
            notify();
        }
    }
}
