package com.example.telegramapi.components.sup.intermidiate_operations;

import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.user.UserSettings;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.SettingsService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NativeComponent {
    private final TelegramBotService telegramService;

    private final SessionService sessionService;

    private final SettingsService settingsService;

    public UserSession handle(UserRequest request){
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        UserSettings settings = settingsService.getSettingsByUsername(session.getUserData().getUser().getUsername());
        String message = request.getUpdate().getMessage().getText();
        if (message.equals("\uD83C\uDDFA\uD83C\uDDE6 Українська")) settings.setNativeLang("uk");
        else if (message.equals("\uD83D\uDC37 Кацапська")) settings.setNativeLang("ru");
        else {
            telegramService.sendMessage(request.getChatId(),
                    "Please choose it from the list!");
            return null;
        }
        settingsService.update(settings.getId(), settings);
        session.getUserData().setUserSettings(settings);
        return session;
    }
}
