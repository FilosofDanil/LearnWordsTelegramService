package com.example.telegramapi.dispatcher;

import com.example.telegramapi.entities.telegram.UserRequest;

public interface Dispatcher {
    void dispatch(UserRequest request);
}
