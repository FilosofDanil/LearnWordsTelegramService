package com.example.telegramapi.entities.user;

import com.example.telegramapi.entities.tests_data.TestEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

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
    private List<Character> lettersList;
    private Map<Character, List<Integer>> replacedMap;
    private Integer quizAttempts;
}
