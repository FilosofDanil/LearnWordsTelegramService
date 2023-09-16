package com.example.telegramapi.components.sup.word_list;

import com.example.telegramapi.components.sup.word_list.UserListCreatorComponent;
import com.example.telegramapi.entities.TranslatedListModel;
import com.example.telegramapi.entities.UserData;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.InlineKeyboardHelper;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReturnListComponent {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final UserListCreatorComponent creator;

    public void sendTest(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        TranslatedListModel translatedListModel = creator.createUserSettings(session, session.getUserData().getPreviousMessage());
        session.setState(States.RETURNED_USER_LIST);
        updateData(session, translatedListModel);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("gotList", lang) + "\n" + translatedListModel.getMessage(), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("testReady", lang), InlineKeyboardHelper.buildInlineKeyboard(List.of(obtainTextService.read("launchQuery", lang)), false));
    }

    private void updateData(UserSession session, TranslatedListModel listModel) {
        UserData userData = session.getUserData();
        userData.setPreviousMessage(listModel.getId());
        session.setUserData(userData);
    }
}
