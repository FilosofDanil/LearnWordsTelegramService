package com.example.dictionaryapi.services;

import com.example.dictionaryapi.entities.TranslatedListModel;

public interface TranslationService {
    TranslatedListModel translate(String text, String langs);
}
