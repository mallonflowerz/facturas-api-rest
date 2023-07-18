package com.mallonflowerz.almacen.facturaDeCompra.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallonflowerz.almacen.facturaDeCompra.models.entity.FacturaCompra;

public interface FacturaCompraRepository extends JpaRepository<FacturaCompra, UUID> {
    
}
