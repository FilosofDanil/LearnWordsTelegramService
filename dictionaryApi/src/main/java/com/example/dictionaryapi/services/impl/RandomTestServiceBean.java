package com.example.dictionaryapi.services.impl;

import com.example.dictionaryapi.components.BotComponent;
import com.example.dictionaryapi.services.RandomTestServiceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RandomTestServiceBean implements RandomTestServiceGenerator {
    private final BotComponent botComponent;

    @Override
    public String formRandomService(Integer amount, String lang, String level) {
        return botComponent.getResponseMessage(formRequest(amount, lang, level));
    }

    private String formRequest(Integer amount, String lang, String level) {
        return "Generate" + amount + " " + lang + " words, which require to know, having " + level + " " + lang + " knowledge level. \n"
                + "Please return just words, without any text or numbers in such format: word1 word2 word3";
    }
}
