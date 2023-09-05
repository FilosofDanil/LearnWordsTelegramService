package com.example.telegramapi.services;

import com.example.telegramapi.entities.TranslatedListModel;

import java.util.List;

public interface GPTInterogativeService {
    TranslatedListModel getTranslation(String message, String langFrom, String langTo);

    List<String> getTests(String word, String lang);
}
