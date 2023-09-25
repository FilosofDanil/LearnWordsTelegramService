package com.example.dictionaryapi.services.impl;

import com.example.dictionaryapi.components.BotComponent;
import com.example.dictionaryapi.services.CheckMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckMessageServiceBean implements CheckMessageService {
    private final BotComponent botComponent;

    @Override
    public String check(String text) {
        return botComponent.getResponseMessage(formRequest(text));
    }

    private String formRequest(String text) {
        return "Please check these words, and give a such feedback: \n" +
                "If the list contains any non-understandable word, or just random letters \n" +
                "Then you must return just word 'error', if all is ok, and akk the words in the given list are correct, you must return 'correct'\n" +
                "Note! You must return it without any redundant text, just the word 'error'/'correct'\n" +
                "So the given list is: \n" +
                text;
    }
}
