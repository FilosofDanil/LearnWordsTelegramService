package com.example.telegramapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSettings {
    @JsonProperty("interfaceLang")
    private String interfaceLang;
    @JsonProperty("nativeLang")
    private String nativeLang;
    @JsonProperty("notifications")
    private Boolean notifications;
    @JsonProperty("user")
    private User user;
}
