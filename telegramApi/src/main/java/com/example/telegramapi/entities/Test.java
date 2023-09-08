package com.example.telegramapi.entities;

import com.example.telegramapi.enums.TestFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Test {
    @JsonProperty("testFormat")
    private TestFormat testFormat;
    @JsonProperty("responseMessage")
    private String responseMessage;
    @JsonProperty("responseKeyboard")
    private List<String> responseKeyboard;
    @JsonProperty("correct")
    private Boolean correct;
    @JsonProperty("correctAnswers")
    private List<String> correctAnswers;
    @JsonProperty("answer")
    private String answer;
}
