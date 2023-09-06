package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.additions.UserListCreatorComponent;
import com.example.telegramapi.entities.*;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.*;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
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

    private final UserListCreatorComponent creator;


    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        session.setState(States.PREPARES_LIST);
        String message = listToString(divideServiceBean.divideRequestString(request.getUpdate().getMessage().getText()));
        saveMessage(message, session);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("waitMoment", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("tryAgain", lang))));
        TranslatedListModel translatedListModel = creator.createUserSettings(session, message);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("gotList", lang) + "\n" + translatedListModel.getMessage());
    }

    private String listToString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder("\n");
        list.forEach(row -> {
            stringBuilder.append(row);
            stringBuilder.append(" ");
        });
        return stringBuilder.toString();
    }

    private void saveMessage(String message, UserSession session){
        UserData userData = session.getUserData();
        userData.setPreviousMessage(message);
        session.setUserData(userData);
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
