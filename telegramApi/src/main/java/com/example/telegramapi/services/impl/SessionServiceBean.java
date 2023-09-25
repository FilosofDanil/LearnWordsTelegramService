package com.example.telegramapi.services.impl;

import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.user.User;
import com.example.telegramapi.entities.user.UserData;
import com.example.telegramapi.entities.user.UserSettings;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.SettingsService;
import com.example.telegramapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@AllArgsConstructor
public class SessionServiceBean implements SessionService {
    private Map<Long, UserSession> sessionMap;

    private final UserService userService;

    private final SettingsService settingsService;

    @Override
    public UserSession getSession(Long chatId) {
        return sessionMap.getOrDefault(chatId, UserSession
                .builder()
                .chatId(chatId)
                .build());
    }

    @Override
    public UserSession checkUseData(UserSession session, UserRequest request) {
        String username;
        String firstName;
        if (request.getUpdate().getMessage() != null) {
            username = request.getUpdate().getMessage().getChat().getUserName();
            firstName = request.getUpdate().getMessage().getChat().getFirstName();
        } else {
            username = request.getUpdate().getCallbackQuery().getMessage().getChat().getUserName();
            firstName = request.getUpdate().getCallbackQuery().getMessage().getChat().getFirstName();
        }
        UserData userData = session.getUserData();
        if (username == null) {
            username = firstName;
        }
        if (userData == null) {
            userData = UserData.builder()
                    .user(userService.getByUsername(username))
                    .userSettings(settingsService.getSettingsByUsername(username))
                    .build();
        }
        if (userData.getUser() == null) createUser(userData, username, firstName, request);

        if (userData.getUserSettings() == null) createSettings(userData, username);
        session.setUserData(userData);
        return session;
    }

    private void createUser(UserData userData, String username, String firstName, UserRequest request) {
        userData.setUser(userService.create(User.builder()
                .username(username)
                .registrationDate(new Date())
                .tgName(firstName)
                .chatId(request.getChatId())
                .build()));
    }

    private void createSettings(UserData userData, String username) {
        userData.setUserSettings(settingsService.create(UserSettings.builder()
                .nativeLang("none")
                .notifications(true)
                .interfaceLang("en")
                .user(userService.getByUsername(username))
                .build()));
    }


    public UserSession saveSession(Long chatId, UserSession session) {
        return sessionMap.put(chatId, session);
    }
}


