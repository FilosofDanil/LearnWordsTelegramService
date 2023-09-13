package com.example.telegramapi.services;

import com.example.telegramapi.entities.TranslatedListModel;

import java.util.List;

public interface GPTInterogativeService {
    TranslatedListModel getTranslation(String message, String langFrom, String langTo);

    String getDetailedTranslate(String word, String langFrom, String langTo);

    List<String> getTests(String word, String lang);

    String getRandomWordList(Integer amount, String lang, String level);
}
