package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.additions.SettingsComponent;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TelegramBotService;
import com.example.telegramapi.utils.InlineKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BackAfterChangesHandler implements TextHandler {
    private final States applicable = States.SUCCESSFULLY_CHANGED_SETTINGS;

    private final SettingsComponent settingsComponent;

    @Override
    public void handle(UserRequest request) {
        String message = request.getUpdate().getMessage().getText();
        if (message.equals("🔙 Back") || message.equals("🔙 Назад")) {
            settingsComponent.handleSettingRequest(request);
        }
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
