package com.mallonflowerz.almacen.configuration.security.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "historial_logins", indexes = @Index(name = "user_uq", columnList = "email", unique = true))
public class HistoryLogins {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String email;

    private LocalDateTime fechaInicioSesion;

    @Column(nullable = false)
    private String token;

}
