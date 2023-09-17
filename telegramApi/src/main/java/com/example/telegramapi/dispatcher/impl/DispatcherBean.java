package com.example.telegramapi.dispatcher.impl;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.dispatcher.Dispatcher;
import com.example.telegramapi.entities.telegram.UserRequest;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DispatcherBean implements Dispatcher {

    private final List<RequestHandler> handlers;

    public DispatcherBean(List<RequestHandler> handlers) {
        this.handlers = handlers
                .stream()
                .sorted(Comparator
                        .comparing(RequestHandler::isGlobal)
                        .reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void dispatch(UserRequest userRequest) {
        for (RequestHandler userRequestHandler : handlers) {
            if (userRequestHandler.isApplicable(userRequest)) {
                userRequestHandler.handle(userRequest);
                return;
            }
        }
    }
}
