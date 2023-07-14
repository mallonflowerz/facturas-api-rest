package com.mallonflowerz.almacen.facturasVentas.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallonflowerz.almacen.facturasVentas.models.entity.DetalleFactura;

public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, UUID> {
    
}
