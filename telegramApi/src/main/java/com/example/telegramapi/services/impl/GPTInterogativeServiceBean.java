package com.example.telegramapi.services.impl;

import com.example.telegramapi.client.GPTClient;
import com.example.telegramapi.entities.TranslatedListModel;
import com.example.telegramapi.services.GPTInterogativeService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GPTInterogativeServiceBean implements GPTInterogativeService {
    private final GPTClient gptClient;

    @Override
    public TranslatedListModel getTranslation(String message, String langFrom, String langTo) {
        String langs = defineLangFrom(langFrom) + "/" + defineLangTo(langTo);
        return gptClient.getMessage(message, langs);
    }

    @Override
    public List<String> getTests(String word, String lang) {
        return gptClient.getTests(word, lang);
    }

    private String defineLangFrom(String langFrom) {
        if (langFrom.equals("en")) {
            langFrom = "English";
        } else if (langFrom.equals("de")) {
            langFrom = "German";
        } else if (langFrom.equals("es")) {
            langFrom = "Spanish";
        } else if (langFrom.equals("fr")) {
            langFrom = "French";
        }
        return langFrom;
    }

    private String defineLangTo(String langTo) {
        if (langTo.equals("en")) {
            langTo = "English";
        } else if (langTo.equals("uk")) {
            langTo = "Ukranian";
        } else if (langTo.equals("ru")) {
            langTo = "Russian";
        } else if (langTo.equals("de")) {
            langTo = "German";
        }
        return langTo;
    }
}
