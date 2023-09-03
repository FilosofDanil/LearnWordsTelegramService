package com.example.telegramapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class TestEntity {
    @JsonProperty("tests")
    private List<Test> tests;
    @JsonProperty("testDate")
    private Date testDate;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("passedTimes")
    private Integer passedTimes;
}
