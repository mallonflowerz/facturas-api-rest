package com.mallonflowerz.almacen.facturaDeCompra.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.facturaDeCompra.models.dto.FacturaCompraDTO;
import com.mallonflowerz.almacen.facturaDeCompra.models.entity.FacturaCompra;
import com.mallonflowerz.almacen.facturaDeCompra.repositories.FacturaCompraRepository;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Tercero;
import com.mallonflowerz.almacen.facturasDeVentas.repositories.TerceroRepository;
import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Producto;
import com.mallonflowerz.almacen.productosYUsuarios.repositories.ProductoRepository;
import com.mallonflowerz.almacen.productosYUsuarios.services.ProductoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FacturaCompraService {

    private final FacturaCompraRepository facturaCompraRepository;
    private final TerceroRepository terceroRepository;
    private final ProductoService productoService;
    private final ProductoRepository productoRepository;

    public Page<FacturaCompra> obtenerFacturas(Pageable pageable) {
        return facturaCompraRepository.findAll(pageable);
    }

    public Optional<FacturaCompra> obtenerFacturaPorId(UUID id) {
        return facturaCompraRepository.findById(id);
    }

    public boolean guardarFactura(FacturaCompraDTO facturaCompraDTO) {
        FacturaCompra facturaCompra = existeFactura(facturaCompraDTO);
        if (facturaCompra != null) {
            facturaCompraRepository.save(facturaCompra);
            return true;
        }
        return false;
    }

    public boolean actualizarFactura(FacturaCompraDTO facturaCompraDTO, UUID id) {
        Optional<FacturaCompra> fact = facturaCompraRepository.findById(id);
        FacturaCompra facturaCompra = existeFactura(facturaCompraDTO);
        if (fact.isPresent() && facturaCompra != null) {
            facturaCompra.setId(id);
            facturaCompraRepository.save(facturaCompra);
            return true;
        }
        return false;
    }

    public boolean eliminarFactura(UUID id) {
        Optional<FacturaCompra> fact = facturaCompraRepository.findById(id);
        if (fact.isPresent()) {
            facturaCompraRepository.deleteById(id);
            fact.get().getProductos().forEach(producto -> {
                productoService.eliminarProductoPorId(producto.getId());
            });
            return true;
        }
        return false;
    }

    private FacturaCompra existeFactura(FacturaCompraDTO facturaCompraDTO) {
        Optional<Tercero> tercero = terceroRepository.findByNit(facturaCompraDTO.getNitTercero());
        List<Producto> productos = facturaCompraDTO.getProductos();
        productos.forEach(producto -> {
            Optional<Producto> option = productoRepository.findByCodigo(producto.getCodigo());
            if (option.isPresent()) {
                option.get().setNombreProducto(producto.getNombreProducto().toUpperCase());
                option.get().setDescripcion(producto.getDescripcion());
                BigDecimal cantidad = option.get().getCantidad().add(producto.getCantidad());
                option.get().setCantidad(cantidad);
                option.get().setValorCompra(producto.getValorCompra());
                option.get().setValorVenta(producto.getValorVenta());
                option.get().setDisponible(producto.getDisponible());
                productoRepository.save(option.get());
            } else {
                productoRepository.save(producto);
            }
        });
        if (tercero.isPresent() && !productos.isEmpty()) {
            FacturaCompra facturaCompra = FacturaCompra.builder()
                    .tercero(tercero.get())
                    .productos(productos)
                    .build();
            return facturaCompra;
        }
        return null;
    }
}
