package com.example.telegramapi.components.sup.word_list;

import com.example.telegramapi.components.sup.data.QueueResolver;
import com.example.telegramapi.entities.gpt.GPTRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.tests_data.TranslatedListModel;
import com.example.telegramapi.entities.tests_data.UserWordList;
import com.example.telegramapi.services.MongoDBService;
import com.example.telegramapi.threads.PreparingRequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserListCreatorComponent {
    private final MongoDBService mongoDBService;

    private final QueueResolver resolver;

    public TranslatedListModel createUserList(UserSession session, String message) throws IllegalArgumentException {
        String langTo = session.getUserData().getUserSettings().getNativeLang();
        String langFrom = session.getUserData().getInputString();
        Long userId = session.getUserData().getUser().getId();
        if (!check(message)) {
            throw new IllegalArgumentException();
        }
        TranslatedListModel translatedListModel = getTranslatedListModel(message, langFrom, langTo);
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
        GPTRequest request = GPTRequest.builder()
                .ready(false)
                .method("check")
                .params(Map.of("message", text))
                .build();
        resolver.putInQueue(request);
        PreparingRequestHandler preparingRequestThread = new PreparingRequestHandler(request);
        preparingRequestThread.start();
        synchronized (preparingRequestThread) {
            try {
                preparingRequestThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        preparingRequestThread.stop();
        String response = resolver.getResponse(request);
        List<String> gptResponse = List.of(response.split(" "));
        return gptResponse.contains("correct");
    }

    private TranslatedListModel getTranslatedListModel(String message, String langFrom, String langTo) {
        GPTRequest request = GPTRequest.builder()
                .ready(false)
                .method("wordList")
                .params(Map.of("message", message, "langFrom", langFrom, "langTo", langTo))
                .build();
        resolver.putInQueue(request);
        PreparingRequestHandler preparingRequestThread = new PreparingRequestHandler(request);
        preparingRequestThread.start();
        synchronized (preparingRequestThread) {
            try {
                preparingRequestThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        preparingRequestThread.stop();
        String response = resolver.getResponse(request);
        Map<String, String> translatedMap = getTranslationMap(response);
        Map<String, String> definitionMap = getDefinitionMap(response);
        return TranslatedListModel.builder()
                .message(response)
                .translatedMap(translatedMap)
                .definitionMap(definitionMap)
                .build();
    }

    private Map<String, String> getDefinitionMap(String message) {
        Map<String, String> definitionMap = new HashMap<>();
        String[] sections = message.split("\n\n");

        // Parse the Definition list
        String[] definitionLines = sections[1].split("\n");
        putInElementsInTheMap(definitionLines, definitionMap);
        return definitionMap;
    }

    private Map<String, String> getTranslationMap(String message) {
        Map<String, String> translationMap = new HashMap<>();
        String[] sections = message.split("\n\n");

        // Parse the Translation list
        String[] translationLines = sections[0].split("\n");
        putInElementsInTheMap(translationLines, translationMap);
        return translationMap;
    }

    private void putInElementsInTheMap(String[] lines, Map<String, String> map) {
        for (int i = 1; i < lines.length; i++) {
            String[] parts = lines[i].split(": ");
            String germanWord = parts[0];
            String ukrainianTranslation = parts[1].trim().replaceAll(",+$", "");
            map.put(germanWord, ukrainianTranslation);
        }

    }
}
