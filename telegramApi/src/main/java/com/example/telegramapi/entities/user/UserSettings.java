package com.example.telegramapi.entities.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSettings {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("interfaceLang")
    private String interfaceLang;
    @JsonProperty("nativeLang")
    private String nativeLang;
    @JsonProperty("notifications")
    private Boolean notifications;
    @JsonProperty("user")
    private User user;
}
