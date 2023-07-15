package com.mallonflowerz.almacen.facturasVentas.models.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "detallefactura")
public class DetalleFactura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nombreProducto;

    @Column(nullable = false, scale = 2, precision = 30)
    private BigDecimal valorVenta;

    @Column(nullable = false, scale = 2, precision = 30)
    private BigDecimal cantidad;

    @Column(nullable = false, scale = 2, precision = 30)
    private BigDecimal valorTotal;

}
