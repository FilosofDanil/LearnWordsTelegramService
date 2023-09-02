package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.entities.UserWordList;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.*;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WordListHandler implements TextHandler {
    private final States applicable = States.WAITING_FOR_LIST;

    private final TelegramBotService telegramService;

    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final DivideService divideServiceBean;

    private final MongoDBService mongoDBService;


    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session = sessionService.checkUseData(session, request);
        Long userID = session.getUserData().getUser().getId();
        session.setState(States.WAITING_FOR_LIST);
        sessionService.saveSession(request.getChatId(), session);
        List<String> definedList = divideServiceBean.divideRequestString(request.getUpdate().getMessage().getText());
        String langFrom = session.getUserData().getUserSettings().getNativeLang();
        String langTo = session.getUserData().getInputString();
        UserWordList returnList = mongoDBService.create(definedList, userID, langFrom, langTo);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("gotList", session.getUserData().getUserSettings().getInterfaceLang()) + returnList.toString());
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
