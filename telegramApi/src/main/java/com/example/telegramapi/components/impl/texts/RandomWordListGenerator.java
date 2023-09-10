package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.additions.MenuComponent;
import com.example.telegramapi.components.additions.ReturnListComponent;
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

    private final ReturnListComponent returnListComponent;

    private final MenuComponent menuComponent;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String message = request.getUpdate().getMessage().getText();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        if (message.equals("🔙 Back") || message.equals("🔙 Назад")) {
            menuComponent.handleMenuRequest(request);
        }
        if (message.equals("🆗 Translate it and start the test!") || message.equals("🆗 Перекласти і почати тест!")) {
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("waitMoment", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("tryAgain", lang))));
            session.setState(States.PREPARES_LIST);
            returnListComponent.sendTest(request);
        }
        if (message.equals("\uD83D\uDD04 Try again") || message.equals("🔄 Надіслати ще раз")) {
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("okCouldTry", lang));
        }
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}