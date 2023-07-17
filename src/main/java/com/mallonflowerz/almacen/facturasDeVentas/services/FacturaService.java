package com.mallonflowerz.almacen.facturasDeVentas.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.facturasDeVentas.models.dto.FacturaDTO;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.DetalleFactura;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Factura;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Tercero;
import com.mallonflowerz.almacen.facturasDeVentas.repositories.DetalleFacturaRepository;
import com.mallonflowerz.almacen.facturasDeVentas.repositories.FacturaRepository;
import com.mallonflowerz.almacen.facturasDeVentas.repositories.TerceroRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final TerceroRepository terceroRepository;
    private final DetalleFacturaRepository detalleRepository;


    public Page<Factura> listarFacturas(Pageable pageable) {
        return facturaRepository.findAll(pageable);
    }

    public Optional<Factura> obtenerFacturaPorId(UUID id) {
        return facturaRepository.findById(id);
    }

    public boolean guardarFactura(FacturaDTO facturaDTO) {
        Factura factura = existeData(facturaDTO);
        if (factura != null){
            facturaRepository.save(factura);
            return true;
        }
        return false;
    }

    public boolean actualizarFactura(UUID id, FacturaDTO facturaDTO) {
        Optional<Factura> facturaOp = facturaRepository.findById(id);
        Factura facturaVerific = existeData(facturaDTO);
        if (facturaOp.isPresent() && facturaVerific != null) {
            facturaVerific.setId(id);
            facturaRepository.save(facturaVerific);
            return true;
        }
        return false;
    }

    public boolean eliminarFactura(UUID id) {
        Optional<Factura> facturaOp = facturaRepository.findById(id);
        if (facturaOp.isPresent()) {
            facturaRepository.delete(facturaOp.get());
            return true;
        }
        return false;
    }

    private Factura existeData(FacturaDTO facturaDTO){
        Optional<Tercero> tercero = terceroRepository.findByNit(facturaDTO.getNitTercero());
        List<DetalleFactura> detalles = detalleRepository.findAllById(facturaDTO.getDetalles());
        if (tercero.isPresent() && !detalles.isEmpty()){
            Factura factura = Factura.builder()
            .tercero(tercero.get())
            .detalles(detalles)
            .build();
            return factura;
        }
        return null;
    }
}
