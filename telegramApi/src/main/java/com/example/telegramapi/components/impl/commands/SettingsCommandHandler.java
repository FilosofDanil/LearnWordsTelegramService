package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.entities.UserSettings;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TelegramBotService;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class SettingsCommandHandler extends RequestHandler {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private static final String command = "/settings";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session = sessionService.checkUseData(session, request);
        session.setState(States.SETTINGS);
        sessionService.saveSession(request.getChatId(), session);
        UserSettings settings = session.getUserData().getUserSettings();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        List<String> replyList = new ArrayList<>(List.of(obtainTextService.read("Rep000", lang), obtainTextService.read("Rep001", lang)));
        if(settings.getNotifications()){
            replyList.add(obtainTextService.read("Rep002", lang));
        }else{
            replyList.add(obtainTextService.read("Rep005", lang));
        }
        replyList.add(obtainTextService.read("Rep003", lang));
        telegramService.sendMessage(request.getChatId(),
                obtainTextService.read("Settings", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
