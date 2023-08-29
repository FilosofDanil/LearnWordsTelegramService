package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TelegramBotService;
import com.example.telegramapi.utils.InlineKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuCommandHandler extends RequestHandler {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private static final String command = "/menu";
    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session.setState(States.MENU);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), "Here is your menu \uD83D\uDC47\uD83C\uDFFB", InlineKeyboardHelper.buildInlineKeyboard(List.of("⚙ Settings" , "ℹ About Bot" , "\uD83C\uDF93 My Assignments", "\uD83E\uDDD1\u200D\uD83D\uDCBB Follow-up tests", "\uD83D\uDE80 Launch test", "✍\uD83C\uDFFB Start learning"), false));
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
