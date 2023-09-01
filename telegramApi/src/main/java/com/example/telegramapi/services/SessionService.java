package com.example.telegramapi.services;

import com.example.telegramapi.entities.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@AllArgsConstructor
public class SessionService {
    private Map<Long, UserSession> sessionMap;

    private final UserService userService;

    private final SettingsService settingsService;

    public UserSession getSession(Long chatId) {
        return sessionMap.getOrDefault(chatId, UserSession
                .builder()
                .chatId(chatId)
                .build());
    }

    public UserSession checkUseData(UserSession session, UserRequest request) {
        String username;
        String firstName;
        if(request.getUpdate().getMessage()!=null){
            username = request.getUpdate().getMessage().getChat().getUserName();
            firstName = request.getUpdate().getMessage().getChat().getFirstName();
        }else{
            username = request.getUpdate().getCallbackQuery().getMessage().getChat().getUserName();
            firstName = request.getUpdate().getCallbackQuery().getMessage().getChat().getFirstName();
        }

        UserData userData = UserData.builder()
                .user(userService.getByUsername(username))
                .userSettings(settingsService.getSettingsByUsername(username))
                .build();
        session.setUserData(userData);
        if (session.getUserData().getUser() == null) {
            userData.setUser(userService.create(User.builder()
                    .username(username)
                    .registrationDate(new Date())
                    .tgName(firstName)
                    .build()));
        }
        if (session.getUserData().getUserSettings() == null) {
            session.getUserData().setUserSettings(settingsService.create(UserSettings.builder()
                    .nativeLang("none")
                    .notifications(true)
                    .interfaceLang("en")
                    .user(userService.getByUsername(username))
                    .build()));
        }
        return session;
    }

    public UserSession saveSession(Long chatId, UserSession session) {
        return sessionMap.put(chatId, session);
    }
}

