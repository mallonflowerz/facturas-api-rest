package com.mallonflowerz.almacen.facturasDeVentas.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Tercero;

public interface TerceroRepository extends JpaRepository<Tercero, UUID> {
    
    Optional<Tercero> findByNit(String nit);
}
