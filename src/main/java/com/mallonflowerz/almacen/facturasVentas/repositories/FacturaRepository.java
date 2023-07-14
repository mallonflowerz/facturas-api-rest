package com.mallonflowerz.almacen.facturasVentas.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallonflowerz.almacen.facturasVentas.models.entity.Factura;

public interface FacturaRepository extends JpaRepository<Factura, UUID> {
    
}
