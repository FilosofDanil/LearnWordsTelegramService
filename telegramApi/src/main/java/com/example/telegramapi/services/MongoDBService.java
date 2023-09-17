package com.example.telegramapi.services;

import com.example.telegramapi.entities.tests_data.UserWordList;

import java.util.List;

public interface MongoDBService {
    List<UserWordList> getAll();

    List<UserWordList> getAllByUserId(Long userID);

    UserWordList getById(String id);

    UserWordList create( UserWordList wordList);

    void delete(Long id);

    void update(String userID, UserWordList wordList);
}
