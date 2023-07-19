package com.mallonflowerz.almacen.facturasDeVentas.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.configuration.exception.ExcesoCantidadException;
import com.mallonflowerz.almacen.facturasDeVentas.models.dto.FacturaDTO;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.DetalleFactura;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Factura;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Tercero;
import com.mallonflowerz.almacen.facturasDeVentas.repositories.DetalleFacturaRepository;
import com.mallonflowerz.almacen.facturasDeVentas.repositories.FacturaRepository;
import com.mallonflowerz.almacen.facturasDeVentas.repositories.TerceroRepository;
import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Producto;
import com.mallonflowerz.almacen.productosYUsuarios.repositories.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final TerceroRepository terceroRepository;
    private final ProductoRepository productoRepository;
    private final DetalleFacturaRepository detalleFacturaRepository;

    public Page<Factura> listarFacturas(Pageable pageable) {
        return facturaRepository.findAll(pageable);
    }

    public Optional<Factura> obtenerFacturaPorId(UUID id) {
        return facturaRepository.findById(id);
    }

    public boolean guardarFactura(FacturaDTO facturaDTO) {
        Factura factura = existeData(facturaDTO);
        if (factura != null) {
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
        List<DetalleFactura> detalles = facturaOp.get().getDetalles();
        if (facturaOp.isPresent()) {
            detalleFacturaRepository.deleteAll(detalles);
            facturaRepository.delete(facturaOp.get());
            return true;
        }
        return false;
    }

    private Factura existeData(FacturaDTO facturaDTO) {
        Optional<Tercero> tercero = terceroRepository.findByNit(facturaDTO.getNitTercero());
        List<DetalleFactura> detalles = facturaDTO.getDetalles();
        detalles.forEach(d -> {
            Optional<Producto> producto = productoRepository.findByCodigo(d.getCodigo());
            if (!producto.isPresent()) {
                throw new EntityNotFoundException(String
                        .format("No existe tal producto con el codigo %s y nombre %s", d.getCodigo(),
                                d.getNombreProducto()));
            } else if (producto.get().getCantidad().compareTo(d.getCantidad()) < 0
                    && !producto.get().isExederLote()) {
                throw new ExcesoCantidadException("La cantidad a vender excede el lote");
            } else {
                d.setValorVenta(producto.get().getValorVenta());
                BigDecimal valorTotal = producto.get().getValorVenta().multiply(d.getCantidad());
                d.setValorTotal(valorTotal);
                BigDecimal cantidad = producto.get().getCantidad().subtract(d.getCantidad());
                producto.get().setCantidad(cantidad);
                productoRepository.save(producto.get());
                detalleFacturaRepository.save(d);
            }
        });
        if (tercero.isPresent() && !detalles.isEmpty()) {
            Factura factura = Factura.builder()
                    .tercero(tercero.get())
                    .detalles(facturaDTO.getDetalles())
                    .build();
            return factura;
        } else {
            throw new EntityNotFoundException("No existe algun tercero con el NIT "+ facturaDTO.getNitTercero());
        }
    }
}
