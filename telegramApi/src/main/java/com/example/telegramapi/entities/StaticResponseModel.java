package com.example.telegramapi.entities;

import com.example.telegramapi.enums.States;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StaticResponseModel {
    private String text;

    private States state;

    private List<String> replyList;

    private boolean inline;
}
