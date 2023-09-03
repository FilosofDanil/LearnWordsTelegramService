package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.TranslatedListModel;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.entities.UserWordList;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.*;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WordListHandler implements TextHandler {
    private final States applicable = States.WAITING_FOR_LIST;

    private final TelegramBotService telegramService;

    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final DivideService divideServiceBean;

    private final MongoDBService mongoDBService;

    private final GPTInterogativeService gptInterogativeService;


    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session = sessionService.checkUseData(session, request);
        Long userID = session.getUserData().getUser().getId();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        session.setState(States.WAITING_FOR_LIST);
        sessionService.saveSession(request.getChatId(), session);
        String langTo = session.getUserData().getUserSettings().getNativeLang();
        String langFrom = session.getUserData().getInputString();
        String message = listToString(divideServiceBean.divideRequestString(request.getUpdate().getMessage().getText()));
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("waitMoment", lang));
        TranslatedListModel translatedListModel = gptInterogativeService.getTranslation(message, langFrom, langTo);
        UserWordList wordList = UserWordList.builder()
                .translations(translatedListModel.getTranslatedMap())
                .definitions(translatedListModel.getDefinitionMap())
                .langTo(langTo)
                .langFrom(langFrom)
                .userId(userID)
                .build();
        mongoDBService.create(wordList);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("gotList", lang) + "\n" + translatedListModel.getMessage());
    }

    private String listToString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder("\n");
        list.forEach(row -> {
            stringBuilder.append(row);
            stringBuilder.append(" ");
        });
        return stringBuilder.toString();
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
