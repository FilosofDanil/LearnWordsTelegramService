package com.example.telegramapi.services;

import com.example.telegramapi.entities.TranslatedListModel;

public interface GPTInterogativeService {
    TranslatedListModel getTranslation(String message, String langFrom, String langTo);
}
