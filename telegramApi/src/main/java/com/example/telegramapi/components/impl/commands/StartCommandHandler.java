package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.entities.*;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class StartCommandHandler extends RequestHandler {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final SettingsService settingsService;

    private final UserService userService;

    private static final String command = "/start";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {

        String username = request.getUpdate().getMessage().getChat().getUserName();
        String firstName = request.getUpdate().getMessage().getChat().getFirstName();
        UserData userData = UserData.builder()
                .user(userService.getByUsername(username))
                .userSettings(settingsService.getSettingsByUsername(username))
                .build();
        if (userData.getUser() == null) {
            userData.setUser(userService.create(User.builder()
                    .username(username)
                    .registrationDate(new Date())
                    .tgName(firstName)
                    .build()));
        }
        if(userData.getUserSettings() == null){
            userData.setUserSettings(settingsService.create(UserSettings.builder()
                    .nativeLang("none")
                    .notifications(true)
                    .interfaceLang("en")
                    .user(userService.getByUsername(username))
                    .build()));
        }
        UserSession session = request.getUserSession();
        session.setState(States.CONVERSATION_STARTED);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(),
                obtainTextService.read("Start", userData.getUserSettings().getInterfaceLang()));
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
