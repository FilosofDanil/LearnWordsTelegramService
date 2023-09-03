package com.example.telegramapi.services;

import com.example.telegramapi.entities.GPTResponseEntity;

public interface GPTInterogativeService {
    GPTResponseEntity getTranslation(String message, String langFrom, String langTo);
}
