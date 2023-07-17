package com.mallonflowerz.almacen.facturasDeVentas.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Factura;

public interface FacturaRepository extends JpaRepository<Factura, UUID> {
    
}
