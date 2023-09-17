package com.example.telegramapi.components.impl.texts.tabs;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.sup.intermidiate_operations.LangBeforeListComponent;
import com.example.telegramapi.components.sup.tab.MenuComponent;
import com.example.telegramapi.components.sup.word_list.RandomMessageSender;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.enums.States;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestTabTextHandler implements TextHandler {
    private final States applicable = States.TEST_TAB;

    private final MenuComponent menuComponent;

    private final RandomMessageSender randomMessageSender;

    private final LangBeforeListComponent langBeforeListComponent;

    @Override
    public void handle(UserRequest request) {
        String message = request.getUpdate().getMessage().getText();
        if (message.equals("➕ New word list")
                || message.equals("➕ Новий список слів")) langBeforeListComponent.requireLang(request);
        else if (message.equals("🚀 Launch random test")
                || message.equals("🚀 Запустити випадковий тест")) {
        } else if (message.equals("🎲 New random word list")
                || message.equals("🎲 Новий список випадкових слів")) randomMessageSender.sendMessage(request);
        else if (message.equals("🔙 Back")
                || message.equals("🔙 Назад")) menuComponent.handleMenuRequest(request);

    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
