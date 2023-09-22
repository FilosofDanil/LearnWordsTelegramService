package com.example.telegramapi.entities.gpt;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class GPTRequest {
    private Map<String, Object> params;
    private String method;
    private Boolean ready;
}
