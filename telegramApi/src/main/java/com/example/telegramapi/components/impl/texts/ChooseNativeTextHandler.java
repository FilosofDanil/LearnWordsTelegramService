package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.entities.UserSettings;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.SettingsService;
import com.example.telegramapi.services.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChooseNativeTextHandler implements TextHandler {
    private final States applicable = States.CHANGE_NATIVE;

    private final SettingsService settingsService;

    private final ObtainTextService obtainTextService;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session = sessionService.checkUseData(session, request);
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        UserSettings settings = settingsService.getSettingsByUsername(session.getUserData().getUser().getUsername());
        String message = request.getUpdate().getMessage().getText();
        if (message.equals("Українська")) {
            settings.setNativeLang("uk");
        } else if (message.equals("English")) {
            settings.setNativeLang("en");
        } else if (message.equals("Русский")) {
            settings.setNativeLang("ru");
        } else if(message.equals("Deutsch")) {
            settings.setNativeLang("de");
        } else if(message.equals("Français")){
            settings.setNativeLang("fr");
        } else if(message.equals("Español")){
            settings.setNativeLang("es");
        } else {
            telegramService.sendMessage(request.getChatId(),
                    "Try one more.");
            return;
        }
        settingsService.update(settings.getId(), settings);
        session.getUserData().setUserSettings(settings);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(),
                obtainTextService.read("ChangedLang", lang));
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
