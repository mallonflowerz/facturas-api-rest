package com.mallonflowerz.almacen.facturasDeVentas.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.facturasDeVentas.models.entity.DetalleFactura;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Factura;
import com.mallonflowerz.almacen.facturasDeVentas.repositories.DetalleFacturaRepository;
import com.mallonflowerz.almacen.facturasDeVentas.repositories.FacturaRepository;
import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Producto;
import com.mallonflowerz.almacen.productosYUsuarios.repositories.ProductoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DetalleFacturaService {

    private final DetalleFacturaRepository dfRepository;
    private final ProductoRepository productoRepository;
    private final FacturaRepository facturaRepository;

    public Page<DetalleFactura> listarDetalleFacturas(Pageable pageable) {
        return dfRepository.findAll(pageable);
    }

    public Optional<DetalleFactura> obtenerDetalleFacturaPorId(UUID id) {
        return dfRepository.findById(id);
    }

    public boolean guardarDetalleFactura(DetalleFactura detalleFactura) {
        DetalleFactura detalleActualizado = detalleConProductos(detalleFactura);
        if (detalleActualizado != null) {
            dfRepository.save(detalleActualizado);
            return true;
        }
        return false;
    }

    public boolean actualizarDetalleFactura(UUID id, DetalleFactura detalleFactura) {
        DetalleFactura detalleActualizado = detalleConProductos(detalleFactura);
        if (detalleActualizado != null) {
            detalleActualizado.setId(id);
            dfRepository.save(detalleActualizado);
            return true;
        }
        return false;
    }

    public boolean eliminarDetalleFactura(UUID id) {
        Optional<DetalleFactura> detalleFacturaOp = dfRepository.findById(id);
        List<Factura> facturas = facturaRepository.findAllByDetalles(detalleFacturaOp.get());
        if (detalleFacturaOp.isPresent() && !facturas.isEmpty()) {
            facturaRepository.deleteAll(facturas);
            dfRepository.delete(detalleFacturaOp.get());
            return true;
        }
        return false;
    }

    private DetalleFactura detalleConProductos(DetalleFactura detalleFactura) {
        Optional<Producto> producto = productoRepository.findByNombreProducto(detalleFactura.getNombreProducto());
        if (producto.isPresent()) {
            detalleFactura.setValorVenta(producto.get().getValorVenta());
            BigDecimal total = producto.get().getValorVenta()
                    .multiply(BigDecimal.valueOf(detalleFactura.getCantidad().doubleValue()));
            detalleFactura.setValorTotal(total);
            return detalleFactura;
        }
        return null;
    }
}
