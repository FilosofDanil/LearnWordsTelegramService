package com.example.telegramapi.components.sup.intermidiate_operations;

import com.example.telegramapi.entities.user.UserData;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LanguageChoosingComponent {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    public UserSession defineListLanguage(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String message = request.getUpdate().getMessage().getText();
        String inputString;
        if (message.equals("\uD83C\uDDEC\uD83C\uDDE7 English")) inputString = "English";
        else if (message.equals("\uD83C\uDDE9\uD83C\uDDEA Deutsch")) inputString = "German";
        else if (message.equals("\uD83C\uDDEB\uD83C\uDDF7 Français")) inputString = "French";
        else if (message.equals("\uD83C\uDDEB\uD83C\uDDF7 Español")) inputString = "Spanish";
        else {
            telegramService.sendMessage(request.getChatId(),
                    "Please pick language from the list!");
            return null;
        }
        UserData userdata = session.getUserData();
        userdata.setInputString(inputString);
        session.setUserData(userdata);
        return session;
    }
}
