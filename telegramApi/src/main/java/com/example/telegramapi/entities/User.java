package com.example.telegramapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class User {
    @JsonProperty("tgName")
    private String tgName;
    @JsonProperty("username")
    private String username;
    @JsonProperty("registrationDate")
    private Date registrationDate;
}
