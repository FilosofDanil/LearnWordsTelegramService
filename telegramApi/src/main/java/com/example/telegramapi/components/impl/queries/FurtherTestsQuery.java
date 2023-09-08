package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
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
public class FurtherTestsQuery implements QueryHandler {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        session.setState(States.FURTHER_QUERY);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), "Not yet!", ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
    }

    @Override
    public String getCallbackQuery(String lang) {
        if (lang.equals("en")) {
            return "👨🏻‍💻 Follow-Up-Tests";
        } else {
            return "👨🏻‍💻 Подальші тести";
        }
    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
