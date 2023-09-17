package com.example.telegramapi.components.impl.texts.backbuttons;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.sup.tab.SettingsComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.enums.States;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BackAfterChangesHandler implements TextHandler {
    private final States applicable = States.SUCCESSFULLY_CHANGED_SETTINGS;

    private final SettingsComponent settingsComponent;

    @Override
    public void handle(UserRequest request) {
        String message = request.getUpdate().getMessage().getText();
        if (message.equals("🔙 Back") || message.equals("🔙 Назад")) settingsComponent.handleSettingRequest(request);
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
