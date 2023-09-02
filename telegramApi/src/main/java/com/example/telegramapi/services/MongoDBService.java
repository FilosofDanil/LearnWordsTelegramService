package com.example.telegramapi.services;

import com.example.telegramapi.entities.UserWordList;

import java.util.List;

public interface MongoDBService {
    List<UserWordList> getAll();

    List<UserWordList> getAllByUserId(Long userID);

    UserWordList getById(Long id);

    UserWordList create(List<String> wordList, Long userId, String langFrom, String langTo);

    void delete(Long id);

    void update(Long userID, UserWordList wordList);
}
