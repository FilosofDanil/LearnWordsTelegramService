package com.example.telegramapi.services.impl;

import com.example.telegramapi.services.DivideService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DivideServiceBean implements DivideService {

    @Override
    public List<String> divideRequestString(String message) {
        return splitStringToList(message);
    }

    private List<String> splitStringToList(String message) {
        List<String> words = new ArrayList<>();
        String cleanedInput = message.replaceAll("[.,;]", " ");
        String[] wordArray = cleanedInput.split("\\s+");
        for (String word : wordArray) {
            words.add(word.trim());
        }

        return words;
    }
}
