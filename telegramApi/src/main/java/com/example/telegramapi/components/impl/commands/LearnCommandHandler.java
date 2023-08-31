package com.example.telegramapi.components.impl.commands;

import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.entities.UserData;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LearnCommandHandler extends RequestHandler {

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private static final String command = "/test";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session = sessionService.checkUseData(session, request);
        String lang = session.getUserData().getUserSettings().getInterfaceLang();

        if (session.getUserData().getUserSettings().getNativeLang().equals("none") || session.getUserData().getUserSettings().getNativeLang() == null) {
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("firstChooseN", lang));
        } else {
            List<String> replyList = List.of(obtainTextService.read("Rep006", lang), obtainTextService.read("Rep007", lang), obtainTextService.read("Rep008", lang), obtainTextService.read("Rep004", lang));
            session.setState(States.TEST_TAB);
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("testTab", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
        }
        sessionService.saveSession(request.getChatId(), session);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
