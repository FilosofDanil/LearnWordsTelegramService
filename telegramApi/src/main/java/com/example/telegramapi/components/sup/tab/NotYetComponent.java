package com.example.telegramapi.components.sup.tab;

import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.user.UserSettings;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotYetComponent {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    public void sendMessage(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session.setState(States.SETTINGS);
        sessionService.saveSession(request.getChatId(), session);
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("notYet", lang));

    }
}
