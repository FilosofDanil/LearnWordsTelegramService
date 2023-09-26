package com.example.databaseapi.services.impl;

import com.example.databaseapi.entities.Settings;
import com.example.databaseapi.entities.Users;
import com.example.databaseapi.repositories.SettingsRepo;
import com.example.databaseapi.services.DBAService;
import com.example.databaseapi.services.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingsServiceBean implements DBAService<Settings>, SettingsService {
    private final SettingsRepo settingsRepo;

    @Override
    public List<Settings> getAll() {
        return settingsRepo.findAll();
    }

    @Override
    public Settings getById(Long id) {
        return settingsRepo.getById(id);
    }

    @Override
    public Settings create(Settings settings) throws IllegalArgumentException{
        if (settingsRepo.findByUser(settings.getUser()) != null) {
            throw new IllegalArgumentException("Trying to create already existing settings!");
        }
        settingsRepo.save(settings);
        return settings;
    }

    @Override
    public void delete(Long id) {
        settingsRepo.deleteById(id);
    }

    @Override
    public void update(Settings settings, Long id) {
        settingsRepo.updateSettings(settings.getId(), settings.getInterfaceLang(), settings.getNativeLang(), settings.getNotifications());
    }

    @Override
    public Settings getSettingsByUser(Users user) {
        return settingsRepo.findByUser(user);
    }
}
