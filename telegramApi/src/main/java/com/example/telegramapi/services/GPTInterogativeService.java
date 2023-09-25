package com.example.telegramapi.services;

import com.example.telegramapi.entities.tests_data.TranslatedListModel;

import java.util.List;

public interface GPTInterogativeService {
    String getTranslation(String message, String langFrom, String langTo);

    String getDetailedTranslate(String word, String langFrom, String langTo);

    String getTests(String word, String lang);

    String getRandomWordList(Integer amount, String lang, String level);

    String check(String text);
}
