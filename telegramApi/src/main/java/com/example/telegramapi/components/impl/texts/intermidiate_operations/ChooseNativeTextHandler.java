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
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        UserSettings settings = settingsService.getSettingsByUsername(session.getUserData().getUser().getUsername());
        String message = request.getUpdate().getMessage().getText();
        if (message.equals("\uD83C\uDDFA\uD83C\uDDE6 Українська")) settings.setNativeLang("uk");
        else if (message.equals("\uD83C\uDDEC\uD83C\uDDE7 English")) settings.setNativeLang("en");
        else if (message.equals("\uD83D\uDC37 Русский")) settings.setNativeLang("ru");
        else if (message.equals("\uD83C\uDDE9\uD83C\uDDEA Deutsch")) settings.setNativeLang("de");
        else {
            telegramService.sendMessage(request.getChatId(),
                    "Try one more.");
            return;
        }
        settingsService.update(settings.getId(), settings);
        session.getUserData().setUserSettings(settings);
        session.setState(States.SUCCESSFULLY_CHANGED_SETTINGS);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(),
                obtainTextService.read("ChangedLang", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
