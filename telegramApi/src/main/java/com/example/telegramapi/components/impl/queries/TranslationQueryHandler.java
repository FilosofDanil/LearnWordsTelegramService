package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.components.sup.translation.TranslationComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TranslationQueryHandler implements QueryHandler {
    private final TranslationComponent translationComponent;

    @Override
    public void handle(UserRequest request) {
        translationComponent.handleTranslationRequest(request);
    }

    @Override
    public String getCallbackQuery(String lang) {
        if (lang.equals("en")) return "📲 Translate and describe words";
        else return "📲 Перекласти та описати слова";
    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
