package com.example.telegramapi.components.impl.texts.intermidiate_operations;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.sup.tab.MenuComponent;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.GPTInterogativeService;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WordBeforeTranslation implements TextHandler {
    private final States applicable = States.WAIT_FOR_WORD;

    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final TelegramBotService telegramService;

    private final MenuComponent menuComponent;

    private final GPTInterogativeService gptService;

    @Override
    public void handle(UserRequest request) {
        String message = request.getUpdate().getMessage().getText();
        if (message.equals("🔙 Back") || request.getUpdate().getMessage().getText().equals("🔙 Назад")) {
            menuComponent.handleMenuRequest(request);
            return;
        }
        UserSession session = sessionService.getSession(request.getChatId());
        String langFrom = session.getUserData().getInputString();
        String langTo = session.getUserData().getUserSettings().getNativeLang();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("waitMoment", lang));
        String response = gptService.getDetailedTranslate(message, langFrom, langTo);
        session.setState(States.SUCCESSFULLY_TRANSLATED);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), response);
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
