package com.example.telegramapi.components.additions;

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
public class TestTabComponent {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    public void handleTestRequest(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();

        if (session.getUserData().getUserSettings().getNativeLang().equals("none") || session.getUserData().getUserSettings().getNativeLang() == null) {
            List<String> replyList = List.of("\uD83C\uDDFA\uD83C\uDDE6 Українська", "\uD83D\uDC37 Кацапська");
            session.setState(States.CHOOSE_NATIVE_BEFORE);
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("firstChooseN", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
        } else {
            List<String> replyList = List.of(obtainTextService.read("Rep006", lang), obtainTextService.read("Rep007", lang), obtainTextService.read("Rep008", lang), obtainTextService.read("Rep004", lang));
            session.setState(States.TEST_TAB);
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("testTab", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
        }
        sessionService.saveSession(request.getChatId(), session);

    }
}
