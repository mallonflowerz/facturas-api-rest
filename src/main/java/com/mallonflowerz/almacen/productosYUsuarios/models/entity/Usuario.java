package com.mallonflowerz.almacen.productosYUsuarios.models.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "usuarios", indexes = @Index(name = "usuario_uq", columnList = "email", unique = true))
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 20, nullable = false)
    private String nombre;

    @Column(nullable = false, length = 25)
    private String apellido;

    @Column(nullable = false, length = 16)
    private String telefono;

    @Column(nullable = false, length = 60)
    private String email;

    @Column(nullable = false, length = 120)
    private String password;

    @ColumnDefault(value = "true")
    private Boolean enabled;

    @ColumnDefault(value = "false")
    private boolean activo;

    @Column(nullable = false, length = 155)
    private String rol;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;
    
    @UpdateTimestamp
    private LocalDateTime ultimaModificacion;

}
