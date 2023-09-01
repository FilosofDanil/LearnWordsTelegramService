package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.components.additions.SettingsComponent;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.entities.UserSettings;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SettingsQueryHandler implements QueryHandler {
    private final SettingsComponent settingsComponent;

    @Override
    public void handle(UserRequest request) {
        settingsComponent.handleSettingRequest(request);
    }

    @Override
    public String getCallbackQuery(String lang) {
        if (lang.equals("uk")) {
            return "⚙️ Налаштування";
        } else {
            return "⚙️ Settings";
        }
    }

    @Override
    public boolean isInteger() {
        return false;
    }
}
