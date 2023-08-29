package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChooseLanguageButton implements TextHandler {
    private final States applicable = States.SETTINGS;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String message = request.getUpdate().getMessage().getText();
        if (message.equals("🇬🇧 Змінити мову інтерфейсу") || message.equals("🇬🇧 Change interface language")) {
            session.setState(States.CHANGE_LANGUAGE);
            String lang = session.getUserData().getUserSettings().getInterfaceLang();
            List<String> replyList = List.of("\uD83C\uDDFA\uD83C\uDDE6 Українська", "\uD83C\uDDEC\uD83C\uDDE7 English");
            telegramService.sendMessage(request.getChatId(),
                    obtainTextService.read("ChooseLanguage", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
        }

    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
