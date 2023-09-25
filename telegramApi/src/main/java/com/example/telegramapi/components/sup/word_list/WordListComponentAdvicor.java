package com.example.telegramapi.components.sup.word_list;

import com.example.telegramapi.entities.user.UserData;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
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

    private final ReturnListComponent returnListComponent;

    public void createWordList(UserRequest request){
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        List<String> elements =divideServiceBean.divideRequestString(request.getUpdate().getMessage().getText());
        if(elements.size() > 25 || elements.size() < 5){
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("notValidAmountWords", lang));
            return;
        }
        session.setState(States.PREPARES_LIST);
        String message = listToString(elements);
        saveMessage(message, session);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("waitMoment", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("tryAgain", lang))));
        returnListComponent.sendTest(request);
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
