package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
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
public class StartCommandHandler extends RequestHandler {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private static final String command = "/start";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session = sessionService.checkUseData(session, request);
        session.setState(States.FIRST_LANGUAGE_CHANGE);
        sessionService.saveSession(request.getChatId(), session);
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        List<String> replyList = List.of("\uD83C\uDDFA\uD83C\uDDE6 Українська","\uD83C\uDDEC\uD83C\uDDE7 English");
        telegramService.sendMessage(request.getChatId(),
                obtainTextService.read("ChooseLanguage", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
