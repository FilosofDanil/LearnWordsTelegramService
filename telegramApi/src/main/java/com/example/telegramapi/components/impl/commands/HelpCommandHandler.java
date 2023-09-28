package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
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
public class HelpCommandHandler extends RequestHandler {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private static final String command = "/help";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        session.setState(States.HELP);
        sessionService.saveSession(request.getChatId(), session);
        List<String> responseQueries = List.of(obtainTextService.read("help1", lang),
                obtainTextService.read("help2", lang),
                obtainTextService.read("help3", lang),
                obtainTextService.read("help4", lang));
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("helpText", lang), InlineKeyboardHelper.buildInlineKeyboard(responseQueries, false));
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
