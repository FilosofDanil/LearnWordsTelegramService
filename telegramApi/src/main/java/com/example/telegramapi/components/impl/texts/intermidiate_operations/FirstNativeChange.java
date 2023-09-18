package com.example.telegramapi.components.impl.texts.intermidiate_operations;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.sup.intermidiate_operations.NativeComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.user.UserSettings;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.SettingsService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FirstNativeChange implements TextHandler {
    private final States applicable = States.FIRST_NATIVE_CHANGE;

    private final TelegramBotService telegramService;

    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final NativeComponent nativeComponent;

    @Override
    public void handle(UserRequest request) {
        UserSession session = nativeComponent.handle(request);
        if (session == null) return;
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        session.setState(States.CONVERSATION_STARTED);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(),
                obtainTextService.read("Start", lang));
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
