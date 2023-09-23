package com.example.telegramapi.components.impl.texts.intermidiate_operations;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.sup.data.QueueResolver;
import com.example.telegramapi.components.sup.tab.MenuComponent;
import com.example.telegramapi.entities.gpt.GPTRequest;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.threads.PreparingRequestHandler;
import com.example.telegramapi.threads.TranslationWaitThread;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WordBeforeTranslation implements TextHandler {
    private final States applicable = States.WAIT_FOR_WORD;

    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final TelegramBotService telegramService;

    private final MenuComponent menuComponent;

    private final QueueResolver resolver;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        if(resolver.usersInQueue() > 0){
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("queueShcedule", lang) + resolver.usersInQueue().toString());
        }
        TranslationWaitThread translationWaitThread = new TranslationWaitThread(sessionService, obtainTextService, telegramService, menuComponent, resolver, request);
        translationWaitThread.start();
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
