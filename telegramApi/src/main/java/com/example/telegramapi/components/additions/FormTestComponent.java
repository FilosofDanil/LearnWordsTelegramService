package com.example.telegramapi.components.additions;

import com.example.telegramapi.entities.Test;
import com.example.telegramapi.entities.TestEntity;
import com.example.telegramapi.entities.UserWordList;
import com.example.telegramapi.enums.TestFormat;
import com.example.telegramapi.services.ObtainTextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FormTestComponent {
    private final ObtainTextService obtainTextService;

    public List<Test> formTest(TestEntity entity, String lang, UserWordList model) {
        Map<String, String> translatedMap = model.getTranslations();
        Map<String, String> definitionMap = model.getDefinitions();
        List<Test> rawTest = entity.getTests();
        rawTest.forEach(row -> {
            String correct = row.getCorrectAnswers().get(0);
            if (row.getTestFormat().equals(TestFormat.SIMPLE_TEXT_FORMAT)) {
                row.setResponseMessage(obtainTextService.read("testText_200", lang) + ":\n" + translatedMap.get(correct));
            } else if (row.getTestFormat().equals(TestFormat.PICK_FROM_LIST_FORMAT)) {
                row.setResponseMessage(obtainTextService.read("testText_201", lang) + ":\n" + translatedMap.get(correct));
            } else if (row.getTestFormat().equals(TestFormat.QUIZ_FORMAT)) {
                row.setResponseMessage(obtainTextService.read("testText_203", lang) + ":\n" + translatedMap.get(correct));
            } else if (row.getTestFormat().equals(TestFormat.BACK_DEFINITION_FORMAT)) {
                row.setResponseMessage(obtainTextService.read("testText_202", lang) + ":\n" + definitionMap.get(correct));
            } else {
                row.setResponseMessage("skip");
            }

        });
        return rawTest;
    }
}
