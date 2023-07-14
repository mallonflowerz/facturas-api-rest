package com.mallonflowerz.almacen.productosYUsuarios.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Producto;

public interface ProductoService {

    Optional<Producto> verProductoPorCodigo(String codigo);

    boolean cambiarDisponible(String codigo, boolean disponible);

    boolean codigoDisponible(String codigo);

    Page<Producto> listarTodosLosProductos(Pageable pageable);
    
    Producto crearProducto(Producto producto);

    Producto modificarProductoPorCodigo(String codigo, Producto producto);

    boolean eliminarProductoPorId(UUID id);
    
}
