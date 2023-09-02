package com.example.dictionaryapi.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class TranslatedListModel {
    private String message;
    private Map<String, String> translatedMap;
    private Map<String, String> definitionMap;
}
