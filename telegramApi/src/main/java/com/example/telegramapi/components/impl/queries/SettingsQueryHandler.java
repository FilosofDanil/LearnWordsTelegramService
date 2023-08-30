package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
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
public class SettingsQueryHandler implements QueryHandler {
    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final TelegramBotService telegramService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session.setState(States.SETTINGS);
        sessionService.saveSession(request.getChatId(), session);
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        List<String> replyList = List.of(obtainTextService.read("Rep000", lang), obtainTextService.read("Rep001", lang), obtainTextService.read("Rep002", lang), obtainTextService.read("Rep003", lang));
        telegramService.sendMessage(request.getChatId(),
                obtainTextService.read("Settings", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
    }

    @Override
    public String getCallbackQuery(String lang) {
        if (lang.equals("uk")) {
            return "⚙️ Налаштування";
        } else {
            return "⚙️ Settings";
        }
    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
