package com.mallonflowerz.almacen.productosYUsuarios.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Usuario;

public interface UsuarioService {

    void updateActivoPorEmail(String email);

    Usuario obtenerUsuarioPorEmail(String email);
    
    Optional<Usuario> obtenerUsuario(UUID id);

    Page<Usuario> obtenerTodosLosUsuarios(Pageable pageable);

    boolean actualizarUsuario(UUID id, Usuario usuario);

    boolean emailDisponible(String email);

    Usuario guardarUsuario(Usuario usuario);

    boolean eliminarUsuario(UUID id);
}
