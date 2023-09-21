package com.example.databaseapi.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.util.Date;

@Table(name = "users")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tg_name")
    @NotBlank
    private String tgName;
    @Column(name = "username")
    @NotNull
    private String username;
    @Column(name = "registration_date")
    @PastOrPresent
    private Date registrationDate;
    @Column(name = "chat_id")
    @NotNull
    private Long chatId;
}
