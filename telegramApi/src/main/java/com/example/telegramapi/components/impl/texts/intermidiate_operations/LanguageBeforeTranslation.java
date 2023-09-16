package com.example.telegramapi.components.impl.texts.intermidiate_operations;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.additions.LanguageChoosingComponent;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LanguageBeforeTranslation implements TextHandler {
    private final States applicable = States.WAITING_FOR_LANG_BEFORE_WORD;

    private final ObtainTextService obtainTextService;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final LanguageChoosingComponent choosingComponent;

    @Override
    public void handle(UserRequest request) {
        UserSession session = choosingComponent.defineListLanguage(request);
        if (session == null) {
            return;
        }
        session.setState(States.WAIT_FOR_WORD);
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("waitForWord", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
