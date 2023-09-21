package com.example.telegramapi.components.sup.word_list;

import com.example.telegramapi.entities.tests_data.TranslatedListModel;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.tests_data.UserWordList;
import com.example.telegramapi.services.GPTInterogativeService;
import com.example.telegramapi.services.MongoDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserListCreatorComponent {
    private final MongoDBService mongoDBService;

    private final GPTInterogativeService gptInterogativeService;

    public TranslatedListModel createUserList(UserSession session, String message) throws IllegalArgumentException {
        String langTo = session.getUserData().getUserSettings().getNativeLang();
        String langFrom = session.getUserData().getInputString();
        Long userId = session.getUserData().getUser().getId();
        TranslatedListModel translatedListModel = gptInterogativeService.getTranslation(message, langFrom, langTo);
        if(translatedListModel.getTranslatedMap().isEmpty() || translatedListModel.getDefinitionMap().isEmpty()){
            throw new IllegalArgumentException();
        } else if(translatedListModel.getTranslatedMap().containsValue("невідомо")
                || translatedListModel.getTranslatedMap().containsValue("помилкове слово")
                || translatedListModel.getTranslatedMap().containsValue("undefined")
                || translatedListModel.getTranslatedMap().containsValue("неизвестно")
                || translatedListModel.getTranslatedMap().containsValue("ошибочное слово")){
            throw new IllegalArgumentException();
        }
        UserWordList wordList = UserWordList.builder()
                .translations(translatedListModel.getTranslatedMap())
                .definitions(translatedListModel.getDefinitionMap())
                .langTo(langTo)
                .langFrom(langFrom)
                .userId(userId)
                .build();
        UserWordList createdList = mongoDBService.create(wordList);
        translatedListModel.setId(createdList.getId());
        return translatedListModel;
    }
}
