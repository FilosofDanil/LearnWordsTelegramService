package com.example.telegramapi.services.impl;

import com.example.telegramapi.client.GPTClient;
import com.example.telegramapi.entities.tests_data.TranslatedListModel;
import com.example.telegramapi.services.GPTInterogativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GPTInterogativeServiceBean implements GPTInterogativeService {
    private final GPTClient gptClient;

    @Override
    public String getTranslation(String message, String langFrom, String langTo) {
        String lang = defineLangFrom(langFrom) + "/" + defineLangTo(langTo);
        return gptClient.getMessage(message, lang);
    }

    @Override
    public String getDetailedTranslate(String word, String langFrom, String langTo) {
        String lang = defineLangFrom(langFrom) + "/" + defineLangTo(langTo);
        return gptClient.getDetailedTranslate(word, lang);
    }

    @Override
    public String getTests(String word, String lang) {
        return gptClient.getTests(word, lang);
    }

    @Override
    public String getRandomWordList(Integer amount, String lang, String level) {
        return gptClient.getRandomWordList(amount, lang, level);
    }

    @Override
    public String check(String text) {
        return gptClient.check(text);
    }

    private String defineLangFrom(String langFrom) {
        if (langFrom.equals("en")) langFrom = "English";
        else if (langFrom.equals("de")) langFrom = "German";
        else if (langFrom.equals("es")) langFrom = "Spanish";
        else if (langFrom.equals("fr")) langFrom = "French";
        return langFrom;
    }

    private String defineLangTo(String langTo) {
        if (langTo.equals("en")) langTo = "English";
        else if (langTo.equals("uk")) langTo = "Ukrainian";
        else if (langTo.equals("ru")) langTo = "Russian";
        else if (langTo.equals("de")) langTo = "German";
        return langTo;
    }
}
