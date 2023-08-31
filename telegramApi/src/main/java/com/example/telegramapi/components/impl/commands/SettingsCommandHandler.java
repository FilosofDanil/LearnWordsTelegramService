package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.components.additions.SettingsComponent;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.entities.UserSettings;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TelegramBotService;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class SettingsCommandHandler extends RequestHandler {
    private final SettingsComponent settingsComponent;

    private static final String command = "/settings";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        settingsComponent.handleSettingRequest(request);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
