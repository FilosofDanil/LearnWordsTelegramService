package com.example.dictionaryapi.services.impl;

import com.example.dictionaryapi.components.BotComponent;
import com.example.dictionaryapi.services.FormTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FormTestServiceBean implements FormTestService {
    private final BotComponent botComponent;

    @Override
    public List<String> createTest(String word, String lang) {
        String message = botComponent.getResponseMessage(formRequest(word, lang));
        List<String> wordList = Arrays.stream(message.split("[,\\s]+"))
                .collect(Collectors.toList());
        if (!wordList.contains(word)) {
            wordList.add(word);
        }
        return wordList;
    }

    private String formRequest(String word, String lang) {
        return "Think of three more words, which seems to the given word, but not mean the same, also you can define at least one absolutely wrong word. You return this three words, in order to me create the guess word test, so consider that it'll be sensible, and it'll be hard to guess the given word in the test. So the given word is(language of the word" +
                " - " + lang + "), so as a return list you also must return it on " +
                lang + ", without losing sense: \n" + word +
                "Return it at such format: word1,word2,word3 givenWord, without any other text.";
    }
}
