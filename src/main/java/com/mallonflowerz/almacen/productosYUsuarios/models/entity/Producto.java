package com.mallonflowerz.almacen.productosYUsuarios.models.entity;

import java.math.BigDecimal;
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
@Table(name = "productos", indexes = @Index(name = "productos_uq", columnList = "codigo", unique = true))
public class Producto {
    
    @Id
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 20)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombreProducto;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false, scale = 2, precision = 30)
    private BigDecimal cantidad;

    @Column(nullable = false, scale = 2, precision = 30)
    private BigDecimal valorCompra;

    @Column(nullable = false, scale = 2, precision = 30)
    private BigDecimal valorVenta;

    @Column(nullable = false)
    private Boolean disponible;

    @ColumnDefault(value = "false")
    private boolean exederLote;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime fechaModificacion;

}
