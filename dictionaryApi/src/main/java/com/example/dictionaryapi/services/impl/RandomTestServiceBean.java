package com.example.dictionaryapi.services.impl;

import com.example.dictionaryapi.components.BotComponent;
import com.example.dictionaryapi.services.RandomTestServiceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RandomTestServiceBean implements RandomTestServiceGenerator {
    private final BotComponent botComponent;

    @Override
    public List<String> formRandomService(Integer amount, String lang, String level) {
        String message = botComponent.getResponseMessage(formRequest(amount, lang, level));
        System.out.println(message);
        List<String> wordList = responseToList(message);
        return wordList;
    }

    private String formRequest(Integer amount, String lang, String level) {
        return "Generate" + amount + " " + lang + " words, which require to know, having " + level + " " + lang + " knowledge level. \n"
                + "Please return just words, without any text or numbers in such format: word1 word2 word3";
    }

    private List<String> responseToList(String message) {
        return null;

    }
}
