package com.mallonflowerz.almacen.facturasVentas.models.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tercero", indexes = @Index(name = "tercero_uq", columnList = "nit", unique = true))
public class Tercero {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 20)
    private String nit;

    @Column(length = 100)
    private String razonSocial;

    @Column(length = 30)
    private String primerNombre;

    @Column(length = 30)
    private String segundoNombre;

    @Column(length = 30)
    private String primerApellido;

    @Column(length = 30)
    private String segundoApellido;

    private String direccion;

    private String ciudadResidencia;

    private String paisResidencia;

    private LocalDate fechaNacimiento;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime ultimaModificacion;

}
