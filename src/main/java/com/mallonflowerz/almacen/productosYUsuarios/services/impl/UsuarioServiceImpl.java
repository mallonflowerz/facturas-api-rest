package com.mallonflowerz.almacen.productosYUsuarios.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Usuario;
import com.mallonflowerz.almacen.productosYUsuarios.repositories.UsuarioRepository;
import com.mallonflowerz.almacen.productosYUsuarios.services.UsuarioService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional
@Service
public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioRepository userRepository;

    @Override
    public Optional<Usuario> obtenerUsuario(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<Usuario> obtenerTodosLosUsuarios(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public boolean emailDisponible(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return userRepository.save(usuario);
    }

    @Override
    public boolean eliminarUsuario(UUID id) {
        Optional<Usuario> o = this.obtenerUsuario(id);
        if (o.isPresent()){
            userRepository.delete(o.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean actualizarUsuario(UUID id, Usuario usuario) {
        Optional<Usuario> u = this.obtenerUsuario(id);
        if (u.isPresent()){
            usuario.setId(id);
            userRepository.save(usuario);
            return true;
        }
        return false;
    }

    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        Optional<Usuario> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            return user.get();
        }
        return null;
    }

    @Override
    public void updateActivoPorEmail(String email) {
        Optional<Usuario> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            user.get().setActivo(!user.get().isActivo());
            userRepository.save(user.get());
        }
    }

}
