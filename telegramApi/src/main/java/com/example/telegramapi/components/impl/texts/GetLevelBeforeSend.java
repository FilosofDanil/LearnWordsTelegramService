package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.UserData;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.GPTInterogativeService;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GetLevelBeforeSend implements TextHandler {
    private final States applicable = States.WAITING_FOR_LEVEL;

    private final static Map<String, String> langMap = Map.of("A1 Elementary", "A1",
            "A2 Pre-Intermediate", "A2",
            "B1 Intermediate", "B1",
            "B2 Upper-Intermediate", "B2",
            "C1 Advanced", "C1");

    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final TelegramBotService telegramService;

    private final GPTInterogativeService gptService;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String message = request.getUpdate().getMessage().getText();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        if (langMap.containsKey(message)) {
            String definedLang = langMap.get(message);
            session.setState(States.GENERATED_RANDOM_LIST);
            sessionService.saveSession(request.getChatId(), session);
            String randomList = getRandomList(session, definedLang);
            telegramService.sendMessage(request.getChatId(), randomList);
        } else {
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("levelEx", lang), ReplyKeyboardHelper.buildMainMenu(replyList()));
        }

    }

    private List<String> replyList() {
        return List.of("A1 Elementary", "A2 Pre-Intermediate", "B1 Intermediate", "B2 Upper-Intermediate", "C1 Advanced");
    }

    private String getRandomList(UserSession session, String level) {
        Integer amount = session.getUserData().getInputInt();
        return gptService.getRandomWordList(amount, "en", level);
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
