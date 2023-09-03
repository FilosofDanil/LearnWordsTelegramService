package com.example.telegramapi.services;

import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;

public interface SessionService {
    UserSession getSession(Long chatId);

    UserSession checkUseData(UserSession session, UserRequest request);

    UserSession saveSession(Long chatId, UserSession session);
}
