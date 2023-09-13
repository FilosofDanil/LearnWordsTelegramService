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
        return "Generate " + amount + " of random  words, which require to know, if you have " + level + " " + lang + " language skills" + " \n"
                + "Note: You must give your response without any other text, just " + amount +" random "+ lang + " words, without numbers or comas, e.t.c. \n Please give it in the such format: word1 word2 word3";
    }
}
