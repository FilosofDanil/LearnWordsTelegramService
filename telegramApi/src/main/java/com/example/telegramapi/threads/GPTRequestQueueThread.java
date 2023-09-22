package com.example.telegramapi.threads;

import com.example.telegramapi.entities.gpt.GPTRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GPTRequestQueueThread extends Thread {
    private final BlockingQueue<GPTRequest> queue = new LinkedBlockingQueue();

    @Override
    public void run() {

    }
}
