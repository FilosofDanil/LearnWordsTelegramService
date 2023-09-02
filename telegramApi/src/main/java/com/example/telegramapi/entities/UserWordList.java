package com.example.telegramapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class UserWordList {
    @JsonProperty("translations")
    private Map<String, String> translations;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("langFrom")
    private String langFrom;
    @JsonProperty("langTo")
    private String langTo;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> keys = translations.keySet();
        keys.forEach(key -> {
            stringBuilder.append("\n");
            stringBuilder.append(key);
            stringBuilder.append(" - ");
            stringBuilder.append(translations.get(key));
        });
        return stringBuilder.toString();
    }
}
