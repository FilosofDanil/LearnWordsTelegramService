package com.example.telegramapi.components.additions;

import com.example.telegramapi.entities.UserData;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LanguageChoosingComponent {
    private final ObtainTextService obtainTextService;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    public UserSession defineListLanguage(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String message = request.getUpdate().getMessage().getText();
        String inputString;
        if (message.equals("\uD83C\uDDEC\uD83C\uDDE7 English")) {
            inputString = "en";
        } else if (message.equals("\uD83C\uDDE9\uD83C\uDDEA Deutsch")) {
            inputString = "de";
        } else if (message.equals("\uD83C\uDDEB\uD83C\uDDF7 Français")) {
            inputString = "fr";
        } else if (message.equals("\uD83C\uDDEB\uD83C\uDDF7 Español")) {
            inputString = "es";
        } else {
            telegramService.sendMessage(request.getChatId(),
                    "Try one more.");
            return null;
        }
        UserData userdata = session.getUserData();
        userdata.setInputString(inputString);
        session.setUserData(userdata);
        return session;
    }
}
