package com.example.mongodbservice.components;

import com.example.mongodbservice.enums.TestFormat;
import com.example.mongodbservice.models.TranslatedList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RandomComponent {
    public Map<String, TestFormat> generateTests(TranslatedList translatedList) {
        Map<String, TestFormat> randomMap = new HashMap<>();
        List<String> values = translatedList.getTranslations().values().stream().toList();
        Collections.shuffle(values);
        int num = values.size();
        int simpleTextFormat = (int) (0.25 * num);
        int testFormat = (int) (0.4 * num);
        int quizFormat = (int) (0.15 * num);
        int backDefinitionFormat = num - simpleTextFormat - testFormat - quizFormat;
        int index = 0;
        while (simpleTextFormat > 0) {
            randomMap.put(values.get(index), TestFormat.SIMPLE_TEXT_FORMAT);
            simpleTextFormat--;
            index++;
        }
        while (testFormat > 0) {
            randomMap.put(values.get(index), TestFormat.PICK_FROM_LIST_FORMAT);
            testFormat--;
            index++;
        }
        while (quizFormat > 0) {
            randomMap.put(values.get(index), TestFormat.QUIZ_FORMAT);
            quizFormat--;
            index++;
        }
        while (backDefinitionFormat > 0) {
            randomMap.put(values.get(index), TestFormat.BACK_DEFINITION_FORMAT);
            backDefinitionFormat--;
            index++;
        }
        return randomMap;
    }
}
