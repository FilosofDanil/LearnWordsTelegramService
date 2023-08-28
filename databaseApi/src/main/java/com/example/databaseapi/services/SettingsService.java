package com.example.databaseapi.services;

import com.example.databaseapi.entities.Settings;
import com.example.databaseapi.entities.Users;

public interface SettingsService {
    Settings getSettingsByUser(Users user);
}
