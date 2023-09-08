package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.UserData;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChooseLangBeforeListHandler implements TextHandler {
    private final States applicable = States.WAIT_FOR_LANG_PAIR;

    private final ObtainTextService obtainTextService;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
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
            return;
        }
        session.setState(States.WAITING_FOR_LIST);
        UserData userdata = session.getUserData();
        userdata.setInputString(inputString);
        session.setUserData(userdata);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("waitList", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));

    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
