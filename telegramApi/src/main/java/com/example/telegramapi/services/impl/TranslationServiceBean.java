package com.example.telegramapi.services.impl;

import com.example.telegramapi.client.MongoClient;
import com.example.telegramapi.entities.UserWordList;
import com.example.telegramapi.services.MongoDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslationServiceBean implements MongoDBService {
    private final MongoClient client;

    @Override
    public List<UserWordList> getAll() {
        return null;
    }

    @Override
    public List<UserWordList> getAllByUserId(Long userID) {
        return client.getAllByUserId(userID);
    }

    @Override
    public UserWordList getById(String id) {
        return client.getWordListById(id);
    }

    @Override
    public UserWordList create( UserWordList wordList) {
        client.create(wordList);
        return wordList;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(String userID, UserWordList wordList) {

    }
}
