package com.mallonflowerz.almacen.facturaDeCompra.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallonflowerz.almacen.facturaDeCompra.models.entity.FacturaCompra;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Tercero;

public interface FacturaCompraRepository extends JpaRepository<FacturaCompra, UUID> {
    List<FacturaCompra> findAllByTercero(Tercero tercero);
}
