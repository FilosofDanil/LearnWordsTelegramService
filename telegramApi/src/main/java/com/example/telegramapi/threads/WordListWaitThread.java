package com.example.telegramapi.threads;

import com.example.telegramapi.components.sup.word_list.UserListCreatorComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.tests_data.TranslatedListModel;
import com.example.telegramapi.entities.user.UserData;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.InlineKeyboardHelper;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WordListWaitThread extends Thread {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final UserListCreatorComponent creator;

    private UserRequest request;

    @Override
    public void run(){
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        try {
            TranslatedListModel translatedListModel = creator.createUserList(session, session.getUserData().getPreviousMessage());
            session.setState(States.RETURNED_USER_LIST);
            updateData(session, translatedListModel);
            sessionService.saveSession(request.getChatId(), session);
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("gotList", lang) + "\n" + translatedListModel.getMessage(), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("testReady", lang), InlineKeyboardHelper.buildInlineKeyboard(List.of(obtainTextService.read("launchQuery", lang)), false));
        } catch (IllegalArgumentException e) {
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("bad_list", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("listFail1", lang),
                    obtainTextService.read("listFail2", lang))));
        }

    }

    private void updateData(UserSession session, TranslatedListModel listModel) {
        UserData userData = session.getUserData();
        userData.setPreviousMessage(listModel.getId());
        session.setUserData(userData);
    }
}
