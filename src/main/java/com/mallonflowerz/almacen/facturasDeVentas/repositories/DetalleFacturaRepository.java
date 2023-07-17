package com.mallonflowerz.almacen.facturasDeVentas.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallonflowerz.almacen.facturasDeVentas.models.entity.DetalleFactura;

public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, UUID> {
    
}
