package com.example.telegramapi.components.impl.texts.intermidiate_operations;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.user.UserSettings;
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
public class ChangeLanguageHandler implements TextHandler {
    private final States applicable = States.CHANGE_LANGUAGE;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final SettingsService settingsService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        UserSettings settings = settingsService.getSettingsByUsername(session.getUserData().getUser().getUsername());
        String message = request.getUpdate().getMessage().getText();
        if (message.equals("\uD83C\uDDFA\uD83C\uDDE6 Українська")) settings.setInterfaceLang("uk");
        else if (message.equals("\uD83C\uDDEC\uD83C\uDDE7 English")) settings.setInterfaceLang("en");
        else if (message.equals("🔙 Back") || message.equals("🔙 Назад")) {
            session.setState(States.SETTINGS);
            sessionService.saveSession(request.getChatId(), session);
            List<String> replyList = List.of(obtainTextService.read("Rep000", lang), obtainTextService.read("Rep001", lang), obtainTextService.read("Rep002", lang), obtainTextService.read("Rep003", lang));
            telegramService.sendMessage(request.getChatId(),
                    obtainTextService.read("Settings", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
            return;
        } else {
            telegramService.sendMessage(request.getChatId(),
                    "Try one more.");
            return;
        }
        updateData(session, settings);
        lang = session.getUserData().getUserSettings().getInterfaceLang();
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(),
                obtainTextService.read("ChangedLang", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
    }

    private void updateData(UserSession session, UserSettings settings) {
        settingsService.update(settings.getId(), settings);
        session.getUserData().setUserSettings(settings);
        session.setState(States.SUCCESSFULLY_CHANGED_SETTINGS);
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
