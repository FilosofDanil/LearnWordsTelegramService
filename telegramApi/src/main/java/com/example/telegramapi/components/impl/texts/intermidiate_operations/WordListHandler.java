package com.example.telegramapi.components.impl.texts.intermidiate_operations;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.sup.tab.MenuComponent;
import com.example.telegramapi.components.sup.word_list.WordListComponentAdvicor;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.enums.States;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WordListHandler implements TextHandler {
    private final States applicable = States.WAITING_FOR_LIST;

    private final WordListComponentAdvicor advicor;

    private final MenuComponent menuComponent;

    @Override
    public void handle(UserRequest request) {
        if(request.getUpdate().getMessage().getText().equals("🔙 Back")
                || request.getUpdate().getMessage().getText().equals("🔙 Назад")) menuComponent.handleMenuRequest(request);
        advicor.createWordList(request);
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
