package com.example.telegramapi.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GPTRequestQueueThread extends Thread {
    private final BlockingQueue queue = new LinkedBlockingQueue();

    @Override
    public void run() {

    }
}
