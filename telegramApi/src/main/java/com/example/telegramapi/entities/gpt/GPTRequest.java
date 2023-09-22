package com.example.telegramapi.entities.gpt;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GPTRequest {
    private String message;
    private String method;
}
