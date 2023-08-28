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
    @Column(name = "tgname")
    private String ticker;
    @Column(name = "username")
    private String username;
    @Column(name = "registrationdate")
    private Date registrationDate;
}
