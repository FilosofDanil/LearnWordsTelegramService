package com.example.telegramapi.components.sup.data;

import com.example.telegramapi.entities.gpt.GPTRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@NoArgsConstructor
public class QueueResolver {
    private final BlockingQueue<GPTRequest> queue = new LinkedBlockingQueue<>();

    public Integer usersInQueue(){
        return queue.size();
    }

    public void putInQueue(){

    }
}
