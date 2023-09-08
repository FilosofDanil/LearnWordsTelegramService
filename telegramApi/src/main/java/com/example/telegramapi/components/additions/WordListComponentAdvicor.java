package com.example.telegramapi.components.additions;

import com.example.telegramapi.entities.TranslatedListModel;
import com.example.telegramapi.entities.UserData;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.DivideService;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WordListComponentAdvicor {
    private final TelegramBotService telegramService;

    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final DivideService divideServiceBean;

    private final UserListCreatorComponent creator;

    public void createWordList(UserRequest request){
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        session.setState(States.PREPARES_LIST);
        String message = listToString(divideServiceBean.divideRequestString(request.getUpdate().getMessage().getText()));
        saveMessage(message, session);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("waitMoment", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("tryAgain", lang))));
        TranslatedListModel translatedListModel = creator.createUserSettings(session, message);
        session.setState(States.RETURNED_USER_LIST);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("gotList", lang) + "\n" + translatedListModel.getMessage(), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
    }

    private String listToString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder("\n");
        list.forEach(row -> {
            stringBuilder.append(row);
            stringBuilder.append(" ");
        });
        return stringBuilder.toString();
    }

    private void saveMessage(String message, UserSession session) {
        UserData userData = session.getUserData();
        userData.setPreviousMessage(message);
        session.setUserData(userData);
    }
}
