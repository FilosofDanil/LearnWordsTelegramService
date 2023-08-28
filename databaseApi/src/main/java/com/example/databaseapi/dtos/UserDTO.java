package com.example.databaseapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserDTO {
    @JsonProperty("tgname")
    private String ticker;
    @JsonProperty("username")
    private String username;
    @JsonProperty("registrationdate")
    private Date registrationDate;
}
