package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.components.sup.tab.SettingsComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
