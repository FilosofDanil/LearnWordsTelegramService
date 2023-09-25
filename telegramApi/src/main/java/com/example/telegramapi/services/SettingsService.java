package com.example.telegramapi.services;

import com.example.telegramapi.entities.user.UserSettings;

import java.util.List;

public interface SettingsService {
    List<UserSettings> getAll();

    UserSettings getById(Long id);

    UserSettings getSettingsByUsername(String username);

    UserSettings create(UserSettings settings);

    void delete(Long id);

    void update(Long id, UserSettings settings);
}
