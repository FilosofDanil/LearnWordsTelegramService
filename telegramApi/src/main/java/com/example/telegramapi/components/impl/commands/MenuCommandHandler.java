package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.InlineKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuCommandHandler extends RequestHandler {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private static final String command = "/menu";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        session.setState(States.MENU);
        sessionService.saveSession(request.getChatId(), session);
        List<String> replyList = List.of(obtainTextService.read("menuBut0", lang), obtainTextService.read("menuBut1", lang), obtainTextService.read("menuBut2", lang), obtainTextService.read("menuBut3", lang), obtainTextService.read("menuBut4", lang), obtainTextService.read("menuBut5", lang));
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("Menu", lang), InlineKeyboardHelper.buildInlineKeyboard(replyList, false));
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
