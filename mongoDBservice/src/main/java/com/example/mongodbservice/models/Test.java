package com.example.mongodbservice.models;

import com.example.mongodbservice.enums.TestFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
@Data
public class Test {
    @JsonProperty("testFormat")
    private TestFormat testFormat;
    @JsonProperty("responseMessage")
    private String responseMessage;
    @JsonProperty("correct")
    private Boolean correct;
    @JsonProperty("correctAnswers")
    private List<String> correctAnswers;
    @JsonProperty("answer")
    private String answer;
}
