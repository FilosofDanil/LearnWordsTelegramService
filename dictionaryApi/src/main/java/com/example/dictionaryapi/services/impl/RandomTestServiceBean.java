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
        return "Generate " + amount + " random " + lang + " words, which require to know, if you have " + level +  " knowledge level. \n"
                + "Note: You must give response without any other text, just words, also without numbers. Please give it in the such format: word1 word2 word3.";
    }
}
