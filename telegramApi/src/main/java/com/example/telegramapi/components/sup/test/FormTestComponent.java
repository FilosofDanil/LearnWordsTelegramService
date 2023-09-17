package com.example.telegramapi.components.sup.test;

import com.example.telegramapi.components.sup.data.LanguageComponent;
import com.example.telegramapi.entities.tests_data.Test;
import com.example.telegramapi.entities.tests_data.TestEntity;
import com.example.telegramapi.entities.tests_data.UserWordList;
import com.example.telegramapi.enums.TestFormat;
import com.example.telegramapi.services.GPTInterogativeService;
import com.example.telegramapi.services.ObtainTextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FormTestComponent {
    private final ObtainTextService obtainTextService;

    private final GPTInterogativeService gptService;

    private final LanguageComponent languageComponent;

    public List<Test> formTest(TestEntity entity, String lang, UserWordList model) {
        Map<String, String> translatedMap = model.getTranslations();
        Map<String, String> definitionMap = model.getDefinitions();
        List<Test> rawTest = entity.getTests();
        rawTest.forEach(row -> {
            String correct = row.getCorrectAnswers().get(0);
            if (row.getTestFormat().equals(TestFormat.SIMPLE_TEXT_FORMAT))
                row.setResponseMessage(obtainTextService.read("testText_200", lang) + ":\n" + translatedMap.get(correct));
            else if (row.getTestFormat().equals(TestFormat.PICK_FROM_LIST_FORMAT)) {
                row.setResponseMessage(obtainTextService.read("testText_201", lang) + ":\n" + translatedMap.get(correct));
                String language = languageComponent.getLang(model.getLangFrom());
                row.setResponseKeyboard(gptService.getTests(correct, language));
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (row.getTestFormat().equals(TestFormat.QUIZ_FORMAT)) {
                row.setResponseMessage(obtainTextService.read("testText_203", lang) + ":\n");
                row.setResponseKeyboard(List.of(obtainTextService.read("gotIt", lang)));
            } else if (row.getTestFormat().equals(TestFormat.BACK_DEFINITION_FORMAT))
                row.setResponseMessage(obtainTextService.read("testText_202", lang) + ":\n" + definitionMap.get(correct));
            else row.setResponseMessage("skip");
        });
        return rawTest;
    }
}
