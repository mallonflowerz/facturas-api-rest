package com.mallonflowerz.almacen.facturasDeVentas.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallonflowerz.almacen.facturasDeVentas.models.entity.DetalleFactura;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Factura;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Tercero;

public interface FacturaRepository extends JpaRepository<Factura, UUID> {
    
    List<Factura> findAllByDetalles(DetalleFactura detalleFactura);

    List<Factura> findAllByTercero(Tercero tercero);
}
