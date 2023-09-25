package com.example.dictionaryapi.services.impl;

import com.example.dictionaryapi.components.BotComponent;
import com.example.dictionaryapi.services.DetailedTranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetailedTranslateBean implements DetailedTranslateService {
    private final BotComponent botComponent;

    @Override
    public String translate(String word, String langs) {
        List<String> languages = Arrays.stream(detectLanguages(langs)).toList();
        String from = languages.get(0);
        String to = languages.get(1);
        return botComponent.getResponseMessage(formRequest(word, from, to));
    }

    private String formRequest(String word, String from, String to) {
        return "Translate please given word: " + word + " from " + from + " to " + to + " \n" +
                "Give completely definition of this word. Also give all the word forms and build 2-3 example sentences with that." +
                "Note: Definitions and sentences also must be translated on " + to +"\n" +
                "So, you should give definitions on the "+to +" Not on English or any other language " +
                "\n  If mine word is unreadable or on other language, return as response error message\";";
    }

    private String[] detectLanguages(String langs) {
        return langs.split("/");
    }
}
