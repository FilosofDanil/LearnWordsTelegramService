package com.example.telegramapi.components;

import com.example.telegramapi.entities.UserRequest;

public interface QueryHandler {
    void handle(UserRequest request);

    String getCallbackQuery(String lang);

    boolean isInteger();
}
