package com.example.telegramapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@Builder
@ToString
public class UserWordList {
    @JsonProperty("translations")
    private Map<String, String> translations;
    @JsonProperty("definitions")
    private Map<String, String> definitions;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("langFrom")
    private String langFrom;
    @JsonProperty("langTo")
    private String langTo;
}
