package com.example.telegramapi.services;

import com.example.telegramapi.entities.GPTResponseEntity;

public interface GPTInterogativeService {
    GPTResponseEntity getTranslation(String message);
}
