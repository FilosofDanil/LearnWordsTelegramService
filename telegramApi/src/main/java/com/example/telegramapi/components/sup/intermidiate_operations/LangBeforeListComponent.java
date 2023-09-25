package com.example.telegramapi.components.sup.intermidiate_operations;

import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
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
public class LangBeforeListComponent {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    public void requireLang(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        session.setState(States.WAIT_FOR_LANG_PAIR);
        List<String> replyList = List.of("\uD83C\uDDEC\uD83C\uDDE7 English", "\uD83C\uDDE9\uD83C\uDDEA Deutsch", "\uD83C\uDDEB\uD83C\uDDF7 Français", "\uD83C\uDDEA\uD83C\uDDF8 Español");
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read(
                "chooseLangList", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
    }
}
