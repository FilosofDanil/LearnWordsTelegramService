package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.additions.UserListCreatorComponent;
import com.example.telegramapi.entities.TranslatedListModel;
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
public class TryAgainBackButton implements TextHandler {
    private final States applicable = States.PREPARES_LIST;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final UserListCreatorComponent creator;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        if(request.getUpdate().getMessage().getText().equals("\uD83D\uDD04 Try again") || request.getUpdate().getMessage().getText().equals("🔄 Надіслати ще раз")){
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("waitMoment", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("tryAgain", lang))));
            TranslatedListModel translatedListModel = creator.createUserSettings(session, session.getUserData().getPreviousMessage());
            session.setState(States.RETURNED_USER_LIST);
            sessionService.saveSession(request.getChatId(), session);
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("gotList", lang) + "\n" + translatedListModel.getMessage(),ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))) );
        }
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
