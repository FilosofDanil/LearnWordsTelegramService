package com.example.telegramapi.components.impl.texts.backbuttons;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.additions.MenuComponent;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.enums.States;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FurtherBackButtonHandler implements TextHandler {
    private final States applicable = States.FURTHER_QUERY;

    private final MenuComponent menuComponent;

    @Override
    public void handle(UserRequest request) {
        if(request.getUpdate().getMessage().getText().equals("🔙 Back") || request.getUpdate().getMessage().getText().equals("🔙 Назад")){
            menuComponent.handleMenuRequest(request);
        }
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
