package com.example.dictionaryapi.services.impl;

import com.example.dictionaryapi.components.BotComponent;
import com.example.dictionaryapi.services.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslationServiceBean implements TranslationService {
    private final BotComponent botComponent;

    @Override
    public String translate(String text, String langs) {
        List<String> languages = Arrays.stream(detectLanguages(langs)).toList();
        String from = languages.get(0);
        String to = languages.get(1);
        return botComponent.getResponseMessage(generateMessage(text, from, to));
    }

    private String[] detectLanguages(String langs) {
        return langs.split("/");
    }

    private String generateMessage(String text, String from, String to) {
        return "Translate that word list from" +
                from + " to " +
                to +
                ", which present in the list: " +
                text +
                "Please return back just translated word list. Required that you respond me it at such format: word1: here u give all the translations of this word; word2 e.t.c.";
    }
}
