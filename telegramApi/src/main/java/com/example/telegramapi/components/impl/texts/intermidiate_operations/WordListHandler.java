package com.example.telegramapi.components.impl.texts.intermidiate_operations;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.additions.MenuComponent;
import com.example.telegramapi.components.additions.UserListCreatorComponent;
import com.example.telegramapi.components.additions.WordListComponentAdvicor;
import com.example.telegramapi.entities.*;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.*;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WordListHandler implements TextHandler {
    private final States applicable = States.WAITING_FOR_LIST;

    private final WordListComponentAdvicor advicor;

    private final MenuComponent menuComponent;

    @Override
    public void handle(UserRequest request) {
        if(request.getUpdate().getMessage().getText().equals("🔙 Back") || request.getUpdate().getMessage().getText().equals("🔙 Назад")){
            menuComponent.handleMenuRequest(request);
        }
        advicor.createWordList(request);
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
