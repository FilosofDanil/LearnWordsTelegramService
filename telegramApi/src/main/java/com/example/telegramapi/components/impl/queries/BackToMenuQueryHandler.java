package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.components.sup.tab.MenuComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BackToMenuQueryHandler implements QueryHandler {
    private final MenuComponent menuComponent;

    @Override
    public void handle(UserRequest request) {
        menuComponent.handleMenuRequest(request);
    }

    @Override
    public String getCallbackQuery(String lang) {
        if (lang.equals("en")) return "Back to menu";
        else return "Назад до меню";
    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
