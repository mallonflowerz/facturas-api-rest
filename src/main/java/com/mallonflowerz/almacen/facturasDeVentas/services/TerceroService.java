package com.mallonflowerz.almacen.facturasDeVentas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.facturaDeCompra.models.entity.FacturaCompra;
import com.mallonflowerz.almacen.facturaDeCompra.repositories.FacturaCompraRepository;
import com.mallonflowerz.almacen.facturaDeCompra.services.FacturaCompraService;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Factura;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Tercero;
import com.mallonflowerz.almacen.facturasDeVentas.repositories.FacturaRepository;
import com.mallonflowerz.almacen.facturasDeVentas.repositories.TerceroRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TerceroService {

    private final TerceroRepository terceroRepository;
    private final FacturaRepository facturaRepository;
    private final FacturaService facturaService;
    private final FacturaCompraRepository facturaCompraRepository;
    private final FacturaCompraService facturaCompraService;

    public Page<Tercero> obtenerTerceros(Pageable pageable) {
        return terceroRepository.findAll(pageable);
    }

    public Optional<Tercero> obtenerTerceroPorNit(String nit) {
        return terceroRepository.findByNit(nit);
    }

    public Tercero guardarTercero(Tercero tercero) {
        return terceroRepository.save(tercero);
    }

    public boolean actualizarTerceroPorNit(String nit, Tercero tercero) {
        Optional<Tercero> terceroOp = terceroRepository.findByNit(nit);
        if (terceroOp.isPresent()) {
            tercero.setId(terceroOp.get().getId());
            terceroRepository.save(tercero);
            return true;
        }
        return false;
    }

    public boolean eliminarTerceroPorNit(String nit) {
        Optional<Tercero> terceroOp = terceroRepository.findByNit(nit);
        List<Factura> facturas = facturaRepository.findAllByTercero(terceroOp.get());
        List<FacturaCompra> facturaCompra = facturaCompraRepository.findAllByTercero(terceroOp.get());
        facturaCompra.forEach(factC -> {
            facturaCompraService.eliminarFactura(factC.getId());
        });
        facturas.forEach(factura -> {
            facturaService.eliminarFactura(factura.getId());
        });
        if (terceroOp.isPresent()) {
            terceroRepository.delete(terceroOp.get());
            return true;
        }
        return false;
    }

}
