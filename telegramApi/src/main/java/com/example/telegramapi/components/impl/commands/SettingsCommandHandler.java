package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TelegramBotService;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SettingsCommandHandler extends RequestHandler {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private static final String command = "/settings";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        UserSession session = request.getUserSession();
        session = sessionService.checkUseData(session, request);
        session.setState(States.SETTINGS);
        sessionService.saveSession(request.getChatId(), session);
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        List<String> replyList = List.of(obtainTextService.read("Rep000", lang), obtainTextService.read("Rep001", lang), obtainTextService.read("Rep002", lang), obtainTextService.read("Rep003", lang));
        telegramService.sendMessage(request.getChatId(),
                obtainTextService.read("Settings", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
