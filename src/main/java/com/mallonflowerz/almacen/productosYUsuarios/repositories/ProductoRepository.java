package com.mallonflowerz.almacen.productosYUsuarios.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, UUID>{
    
    Optional<Producto> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    Optional<Producto> findByNombreProducto(String nombre);
}
