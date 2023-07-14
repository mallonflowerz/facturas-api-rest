package com.mallonflowerz.almacen.productosYUsuarios.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Producto;
import com.mallonflowerz.almacen.productosYUsuarios.repositories.ProductoRepository;
import com.mallonflowerz.almacen.productosYUsuarios.services.ProductoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public Optional<Producto> verProductoPorCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo);
    }

    @Override
    public Page<Producto> listarTodosLosProductos(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto modificarProductoPorCodigo(String codigo, Producto producto) {
        Optional<Producto> p = productoRepository.findByCodigo(codigo);
        if (p.isPresent()) {
            producto.setId(p.get().getId());
            return productoRepository.save(producto);
        }
        return null;
    }

    @Override
    public boolean eliminarProductoPorId(UUID id) {
        if (productoRepository.findById(id).isPresent()) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean codigoDisponible(String codigo) {
        if (!productoRepository.existsByCodigo(codigo)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean cambiarDisponible(String codigo, boolean disponible) {
        Optional<Producto> producto = productoRepository.findByCodigo(codigo);
        if (producto.isPresent()){
            producto.get().setDisponible(disponible);
            productoRepository.save(producto.get());
            return true;
        }
        return false;
    }

}
