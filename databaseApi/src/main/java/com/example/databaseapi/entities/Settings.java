package com.example.databaseapi.entities;

import jakarta.persistence.*;
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
    private String interfaceLang;
    @Column(name = "native")
    private String nativeLang;
    @Column(name = "notifications")
    private Boolean notifications;
    @OneToOne()
    @JoinColumn(name = "userId", nullable = false, referencedColumnName = "id")
    private Users user;
}
