package com.example.telegramapi.services.impl;

import com.example.telegramapi.client.DataClient;
import com.example.telegramapi.entities.user.UserSettings;
import com.example.telegramapi.services.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingsServiceBean implements SettingsService {
    private final DataClient dataClient;

    @Override
    public List<UserSettings> getAll() {
        return dataClient.getAllSettings();
    }

    @Override
    public UserSettings getById(Long id) {
        return dataClient.getSettingsById(id);
    }

    @Override
    public UserSettings getSettingsByUsername(String username) {
        return dataClient.getSettingsByUsername(username);
    }

    @Override
    public UserSettings create(UserSettings settings) {
        return dataClient.create(settings);
    }

    @Override
    public void delete(Long id) {
        dataClient.deleteSettings(id);
    }

    @Override
    public void update(Long id, UserSettings settings) {
        dataClient.updateSettings(id, settings);
    }
}
