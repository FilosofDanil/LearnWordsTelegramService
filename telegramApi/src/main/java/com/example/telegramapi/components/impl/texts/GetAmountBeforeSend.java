package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.UserData;
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
public class GetAmountBeforeSend implements TextHandler {
    private final States applicable = States.WAITING_FOR_AMOUNT;

    private final TelegramBotService telegramService;

    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String message = request.getUpdate().getMessage().getText();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        try {
            Integer amount = Integer.parseInt(message);
            saveUserData(amount, session);
            sessionService.saveSession(request.getChatId(), session);
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("randWaitLevel", lang), ReplyKeyboardHelper.buildMainMenu(replyList()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("numEx", lang));
        }
    }

    private void saveUserData(Integer amount, UserSession session) {
        UserData userData = session.getUserData();
        userData.setInputInt(amount);
        session.setUserData(userData);
    }

    private List<String> replyList() {
        return List.of("A1 Elementary", "A2 Pre-Intermediate", "B1 Intermediate", "B2 Upper-Intermediate", "C1 Advanced");
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
