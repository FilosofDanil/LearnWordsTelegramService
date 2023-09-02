package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TelegramBotService;
import com.example.telegramapi.utils.InlineKeyboardHelper;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TestTabTextHandler implements TextHandler {
    private final States applicable = States.TEST_TAB;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session = sessionService.checkUseData(session, request);
        String message = request.getUpdate().getMessage().getText();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        if (message.equals("➕ New word list") || message.equals("➕ Новий список слів")) {
            session.setState(States.WAIT_FOR_LANG_PAIR);
            List<String> replyList = List.of("\uD83C\uDDEC\uD83C\uDDE7 English", "\uD83C\uDDE9\uD83C\uDDEA Deutsch", "\uD83C\uDDEB\uD83C\uDDF7 Français", "\uD83C\uDDEB\uD83C\uDDF7 Español");
            sessionService.saveSession(request.getChatId(), session);
            telegramService.sendMessage(request.getChatId(), obtainTextService.read(
                    "chooseLangList", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
        } else if (message.equals("🚀 Launch random test") || message.equals("🚀 Запустити випадковий тест")) {

        } else if (message.equals("🎲 New random word list") || message.equals("🎲 Новий список випадкових слів")) {
            session.setState(States.RANDOM_LIST_WAITING_FOR_NUM);
            sessionService.saveSession(request.getChatId(), session);
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("randWaitNum", lang));
        } else if (message.equals("🔙 Back") || message.equals("🔙 Назад")) {
            session.setState(States.MENU);
            sessionService.saveSession(request.getChatId(), session);
            List<String> replyList = List.of(obtainTextService.read("menuBut0", lang), obtainTextService.read("menuBut1", lang), obtainTextService.read("menuBut2", lang), obtainTextService.read("menuBut3", lang), obtainTextService.read("menuBut4", lang), obtainTextService.read("menuBut5", lang));
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("Menu", lang), InlineKeyboardHelper.buildInlineKeyboard(replyList, false));
        }
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
