package com.example.telegramapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class User {
    @JsonProperty("tgname")
    private String tgname;
    @JsonProperty("username")
    private String username;
    @JsonProperty("registrationdate")
    private Date registrationDate;
}
