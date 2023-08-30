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
public class ChooseLanguageButton implements TextHandler {
    private final States applicable = States.SETTINGS;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session = sessionService.checkUseData(session, request);
        String message = request.getUpdate().getMessage().getText();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        if (message.equals("🇬🇧 Змінити мову інтерфейсу") || message.equals("🇬🇧 Change interface language")) {
            session.setState(States.CHANGE_LANGUAGE);
            List<String> replyList = List.of("\uD83C\uDDFA\uD83C\uDDE6 Українська", "\uD83C\uDDEC\uD83C\uDDE7 English", obtainTextService.read("Rep004", lang));
            telegramService.sendMessage(request.getChatId(),
                    obtainTextService.read("ChooseLanguage", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
        } else if (message.equals("🔙 Back to the menu tab") || message.equals("🔙 Повернутися до вкладки меню")) {
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
