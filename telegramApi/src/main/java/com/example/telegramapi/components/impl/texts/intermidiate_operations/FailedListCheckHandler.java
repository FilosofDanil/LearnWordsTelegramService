package com.example.telegramapi.components.impl.texts.intermidiate_operations;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.sup.word_list.UserListCreatorComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FailedListCheckHandler implements TextHandler {
    private final States applicable = States.WARNING;

    private final TelegramBotService telegramService;

    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final UserListCreatorComponent creator;

    @Override
    public void handle(UserRequest request) {
        try{

        } catch (IllegalArgumentException e){

        }
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
