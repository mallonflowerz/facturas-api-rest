package com.mallonflowerz.almacen.facturasVentas.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallonflowerz.almacen.facturasVentas.models.entity.Tercero;

public interface TerceroRepository extends JpaRepository<Tercero, UUID> {
    
}
