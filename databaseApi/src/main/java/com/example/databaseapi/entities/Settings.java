package com.example.databaseapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Table(name = "settings")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "interfacelang")
    @NotNull
    private String interfaceLang;
    @Column(name = "native")
    private String nativeLang;
    @Column(name = "notifications")
    @NotNull
    private Boolean notifications;
    @OneToOne()
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @NotNull
    private Users user;
}
