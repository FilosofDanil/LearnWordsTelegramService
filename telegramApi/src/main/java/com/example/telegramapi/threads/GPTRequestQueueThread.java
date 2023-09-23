package com.example.telegramapi.threads;

import com.example.telegramapi.entities.gpt.GPTRequest;
import com.example.telegramapi.services.GPTInterogativeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
@RequiredArgsConstructor
public class GPTRequestQueueThread extends Thread {
    private final BlockingQueue<GPTRequest> queue = new LinkedBlockingQueue();

    private final Map<GPTRequest, String> responseMap = new HashMap<>();

    private final GPTInterogativeService gptService;

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            long startTime = System.currentTimeMillis();
            GPTRequest request = queue.take();
            String response = operate(request.getParams(), request.getMethod());
            request.setReady(true);
            responseMap.put(request, response);
            long endTime = System.currentTimeMillis();
            long timeElapsed = endTime - startTime;
            if (timeElapsed <= 20001) {
                Thread.sleep(20001 - timeElapsed);
            }
        }
    }

    public String operate(Map<String, Object> params, String operation) {
        if (operation == null) return "skip";
        if (operation.equals("translate"))
            return gptService.getDetailedTranslate((String) params.get("message"), (String) params.get("langFrom"), (String) params.get("langTo"));
        else if (operation.equals("wordList"))
            return gptService.getTranslation((String) params.get("message"), (String) params.get("langFrom"), (String) params.get("langTo"));
        else if (operation.equals("check"))
            return gptService.check((String) params.get("message"));
        else if (operation.equals("random"))
            return gptService.getRandomWordList((Integer) params.get("amount"), (String) params.get("lang"), (String) params.get("level"));
        else if (operation.equals("tests"))
            return gptService.getTests((String) params.get("word"), (String) params.get("lang"));
        else return "skip";
    }
}
