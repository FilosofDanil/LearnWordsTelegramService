package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.components.sup.tab.MenuComponent;
import com.example.telegramapi.entities.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuCommandHandler extends RequestHandler {
    private final MenuComponent menuComponent;

    private static final String command = "/menu";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        menuComponent.handleMenuRequest(request);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
