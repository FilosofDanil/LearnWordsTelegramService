package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.additions.LangBeforeListComponent;
import com.example.telegramapi.components.additions.MenuComponent;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
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

    private final MenuComponent menuComponent;

    private final LangBeforeListComponent langBeforeListComponent;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String message = request.getUpdate().getMessage().getText();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        if (message.equals("➕ New word list") || message.equals("➕ Новий список слів")) {
           langBeforeListComponent.requireLang(request);
        } else if (message.equals("🚀 Launch random test") || message.equals("🚀 Запустити випадковий тест")) {

        } else if (message.equals("🎲 New random word list") || message.equals("🎲 Новий список випадкових слів")) {
            session.setState(States.RANDOM_LIST_WAITING_FOR_NUM);
            sessionService.saveSession(request.getChatId(), session);
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("randWaitNum", lang));
        } else if (message.equals("🔙 Back") || message.equals("🔙 Назад")) {
            menuComponent.handleMenuRequest(request);
        }
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
