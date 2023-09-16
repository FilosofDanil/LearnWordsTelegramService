package com.example.telegramapi.components.impl.texts.intermidiate_operations;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.entities.UserSettings;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.SettingsService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FirstLanguageChange implements TextHandler {
    private final States applicable = States.FIRST_LANGUAGE_CHANGE;

    private final TelegramBotService telegramService;

    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final SettingsService settingsService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        UserSettings settings = settingsService.getSettingsByUsername(session.getUserData().getUser().getUsername());
        String message = request.getUpdate().getMessage().getText();
        if(message.equals("\uD83C\uDDFA\uD83C\uDDE6 Українська")){
            settings.setInterfaceLang("uk");
        } else if(message.equals("\uD83C\uDDEC\uD83C\uDDE7 English")){
            settings.setInterfaceLang("en");
        } else{
            telegramService.sendMessage(request.getChatId(),
                    "Try one more.");
            return;
        }
        settingsService.update(settings.getId(), settings);
        session.getUserData().setUserSettings(settings);
        session.setState(States.CONVERSATION_STARTED);
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(),
                obtainTextService.read("Start", lang));
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
