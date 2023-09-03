package com.example.telegramapi.services.impl;

import com.example.telegramapi.client.GPTClient;
import com.example.telegramapi.entities.GPTResponseEntity;
import com.example.telegramapi.services.GPTInterogativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GPTInterogativeServiceBean implements GPTInterogativeService {
    private final GPTClient gptClient;

    @Override
    public GPTResponseEntity getTranslation(String message, String langFrom, String langTo) {
        String langs = langFrom + "/" + langTo;
        return gptClient.getMessage(message, langs);
    }
}
