package com.example.telegramapi.commandlinerunners;

import com.example.telegramapi.threads.GPTRequestQueueThread;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ThreadRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        GPTRequestQueueThread queueThread = new GPTRequestQueueThread();
        queueThread.start();
    }
}
