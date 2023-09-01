package com.example.telegramapi.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserData {
    private User user;
    private UserSettings userSettings;
    private String inputString;
}
