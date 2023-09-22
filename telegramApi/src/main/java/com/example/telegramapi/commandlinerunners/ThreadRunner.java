package com.example.telegramapi.commandlinerunners;

import com.example.telegramapi.components.sup.data.QueueResolver;
import com.example.telegramapi.services.GPTInterogativeService;
import com.example.telegramapi.threads.GPTRequestQueueThread;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ThreadRunner implements CommandLineRunner {
    private final QueueResolver queueResolver;

    private final GPTInterogativeService gptService;

    @Override
    public void run(String... args) throws Exception {
        GPTRequestQueueThread queueThread = new GPTRequestQueueThread(gptService);
        queueResolver.initQueue(queueThread.getQueue());
        queueResolver.initMap(queueThread.getResponseMap());
        queueThread.start();
    }
}
