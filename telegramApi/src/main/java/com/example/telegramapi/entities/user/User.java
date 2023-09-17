package com.example.telegramapi.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class User {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("tgName")
    private String tgName;
    @JsonProperty("username")
    private String username;
    @JsonProperty("registrationDate")
    private Date registrationDate;
    @JsonProperty("chatId")
    private Long chatId;
}
