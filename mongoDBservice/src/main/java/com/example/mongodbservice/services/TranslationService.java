package com.example.mongodbservice.services;

import com.example.mongodbservice.models.TranslatedList;

import java.util.List;

public interface TranslationService {
    List<TranslatedList> getAll();

    List<TranslatedList> getAllByUser(Long userId);

    TranslatedList getById(String id);

    TranslatedList create(TranslatedList translatedList);

    void delete(Long id);

    void update(TranslatedList translatedList, String id);
}
