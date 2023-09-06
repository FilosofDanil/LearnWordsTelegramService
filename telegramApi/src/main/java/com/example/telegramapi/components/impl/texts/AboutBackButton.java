package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.additions.MenuComponent;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.enums.States;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AboutBackButton implements TextHandler {
    private final States applicable = States.ABOUT;

    private final MenuComponent menuComponent;

    @Override
    public void handle(UserRequest request) {
        menuComponent.handleMenuRequest(request);
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
