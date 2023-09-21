package com.example.telegramapi.components.sup.word_list;

import com.example.telegramapi.entities.tests_data.TranslatedListModel;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.tests_data.UserWordList;
import com.example.telegramapi.services.GPTInterogativeService;
import com.example.telegramapi.services.MongoDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserListCreatorComponent {
    private final MongoDBService mongoDBService;

    private final GPTInterogativeService gptInterogativeService;

    public TranslatedListModel createUserList(UserSession session, String message) throws IllegalArgumentException {
        String langTo = session.getUserData().getUserSettings().getNativeLang();
        String langFrom = session.getUserData().getInputString();
        Long userId = session.getUserData().getUser().getId();
        if (!check(message)) {
            throw new IllegalArgumentException();
        }
        TranslatedListModel translatedListModel = gptInterogativeService.getTranslation(message, langFrom, langTo);
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

    private boolean check(String text) {
        String response = gptInterogativeService.check(text);
        List<String> gptResponse = List.of(response.split(" "));
        return gptResponse.contains("correct");
    }
}
