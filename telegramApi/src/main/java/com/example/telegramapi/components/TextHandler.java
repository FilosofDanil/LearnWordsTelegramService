package com.example.telegramapi.components;

import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.enums.States;

public interface TextHandler {
    void handle(UserRequest request);

    States getApplicableState();
}
