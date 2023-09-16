package com.example.telegramapi.components.impl.queries;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.components.sup.tab.SettingsComponent;
import com.example.telegramapi.entities.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
