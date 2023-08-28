package com.example.telegramapi.entities;

import com.example.telegramapi.enums.States;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSession {
    private Long chatId;
    private UserData userData;
    private States state;
}
