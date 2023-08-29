package com.example.telegramapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserSettings {
    @JsonProperty("interfacelang")
    private String interfaceLang;
    @JsonProperty("native")
    private String nativeLang;
    @JsonProperty("notifications")
    private Boolean notifications;
    @JsonProperty("user")
    private Long userId;
}
