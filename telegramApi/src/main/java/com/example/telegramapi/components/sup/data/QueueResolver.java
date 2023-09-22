package com.example.telegramapi.components.sup.data;

import com.example.telegramapi.entities.gpt.GPTRequest;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

@Component
@NoArgsConstructor
public class QueueResolver {
    private BlockingQueue<GPTRequest> queue;

    private Map<GPTRequest, String> responseMap;

    public void initQueue(BlockingQueue<GPTRequest> queue) {
        this.queue = queue;
    }

    public void initMap(Map<GPTRequest, String> responseMap) {
        this.responseMap = responseMap;
    }

    public String getResponse(GPTRequest request) {
        String response = responseMap.get(request);
        responseMap.remove(request);
        return response;
    }

    public Integer usersInQueue() {
        return queue.size();
    }

    public void putInQueue(GPTRequest request) {
        queue.add(request);
    }

    public void removeFromQueue(GPTRequest request) {
        queue.remove(request);
    }
}
