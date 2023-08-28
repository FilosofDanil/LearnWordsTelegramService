package com.example.databaseapi.entities;


import jakarta.persistence.*;
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
    private String tgName;
    @Column(name = "username")
    private String username;
    @Column(name = "registration_date")
    private Date registrationDate;
}
