package com.example.dictionaryapi.services.impl;

import com.example.dictionaryapi.components.BotComponent;
import com.example.dictionaryapi.services.FormTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FormTestServiceBean implements FormTestService {
    private final BotComponent botComponent;

    @Override
    public String createTest(String word, String lang) {
        return botComponent.getResponseMessage(formRequest(word, lang));
    }

    private String formRequest(String word, String lang) {
        return "Think of three more words, which seems to the given word, but not mean the same, also you can define at least one absolutely wrong word. You return this three words, in order to me create the guess word test, so consider that it'll be sensible, and it'll be hard to guess the given word in the test. So the given word is(language of the word" +
                " - " + lang + "), so as a return list you also must return it on " +
                lang + ", without losing sense: \n" + word +
                "Return it at such format: word1,word2,word3 givenWord, without any other text, and without numbers, and no more than 3 words.";
    }
}
