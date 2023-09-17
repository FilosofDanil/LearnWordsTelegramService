package com.example.telegramapi.components.impl.texts.intermidiate_operations;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.sup.intermidiate_operations.LanguageChoosingComponent;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetLangBeforeRand implements TextHandler {
    private final States applicable = States.WAITING_FOR_LANG_BEFORE_RANDOM;

    private final ObtainTextService obtainTextService;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final LanguageChoosingComponent choosingComponent;

    @Override
    public void handle(UserRequest request) {
        UserSession session = choosingComponent.defineListLanguage(request);
        if (session == null) return;
        session.setState(States.WAITING_FOR_AMOUNT);
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("randWaitNum", lang));
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
