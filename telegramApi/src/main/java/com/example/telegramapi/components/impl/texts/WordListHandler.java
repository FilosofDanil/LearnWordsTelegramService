package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.GPTResponseEntity;
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
        session.setState(States.WAITING_FOR_LIST);
        sessionService.saveSession(request.getChatId(), session);
        String langFrom = session.getUserData().getUserSettings().getNativeLang();
        String langTo = session.getUserData().getInputString();
        String message = listToString(divideServiceBean.divideRequestString(request.getUpdate().getMessage().getText()));
        GPTResponseEntity gptResponseEntity = gptInterogativeService.getTranslation(message, langFrom, langTo);
        UserWordList wordList = UserWordList.builder()
                .translations(gptResponseEntity.getTranslatedMap())
                .definitions(gptResponseEntity.getDefinitionMap())
                .langTo(langTo)
                .langFrom(langFrom)
                .build();
        mongoDBService.create(wordList);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("gotList", session.getUserData().getUserSettings().getInterfaceLang()) + gptResponseEntity.getMessage());
    }

    private String listToString(List<String> list){
        StringBuilder stringBuilder = new StringBuilder("\n");
        list.forEach(row ->{
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
