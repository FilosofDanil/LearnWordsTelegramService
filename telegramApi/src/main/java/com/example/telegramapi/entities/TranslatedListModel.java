package com.example.telegramapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class TranslatedListModel {
    @JsonIgnore
    private String id;
    @JsonProperty("message")
    private String message;
    @JsonProperty("translatedMap")
    private Map<String, String> translatedMap;
    @JsonProperty("definitionMap")
    private Map<String, String> definitionMap;
}
