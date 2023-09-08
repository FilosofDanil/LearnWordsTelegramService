package com.example.telegramapi.entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserData {
    private User user;
    private UserSettings userSettings;
    private String inputString;
    private String previousMessage;
    private TestEntity currentTest;
    private Integer currentTask;
    private Integer inputInt;
    private List<String> randList;
}
