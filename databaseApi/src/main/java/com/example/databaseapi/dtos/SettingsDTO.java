package com.example.databaseapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingsDTO {
    @JsonProperty("interfacelang")
    private String ticker;
    @JsonProperty("native")
    private String nativeLang;
    @JsonProperty("notifications")
    private Boolean notifications;
}
