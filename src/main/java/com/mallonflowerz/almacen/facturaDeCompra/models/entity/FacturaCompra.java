package com.mallonflowerz.almacen.facturaDeCompra.models.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Tercero;
import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Producto;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "factura_compra")
public class FacturaCompra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Tercero tercero;

    @ManyToMany
    private List<Producto> productos;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaDefactura;

}
