package com.example.telegramapi.components.sup.intermidiate_operations;

import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreparingTestComponent {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    public void prepareTest(UserRequest request){
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        session.setState(States.PREPARES_TEST);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read(
                "PreparingTest", lang));
    }
}
