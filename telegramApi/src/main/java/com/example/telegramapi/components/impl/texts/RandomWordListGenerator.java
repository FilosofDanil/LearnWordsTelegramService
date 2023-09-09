package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.additions.MenuComponent;
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
public class RandomWordListGenerator implements TextHandler {
    private final States applicable = States.GENERATED_RANDOM_LIST;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final UserListCreatorComponent creator;

    private final MenuComponent menuComponent;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String message = request.getUpdate().getMessage().getText();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        if(message.equals("🔙 Back") || message.equals("🔙 Назад")){
            menuComponent.handleMenuRequest(request);
        } if(message.equals("🆗 Translate it and start the test!") || message.equals("🆗 Перекласти і почати тест!")){
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("waitMoment", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("tryAgain", lang))));
            session.setState(States.PREPARES_LIST);
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
