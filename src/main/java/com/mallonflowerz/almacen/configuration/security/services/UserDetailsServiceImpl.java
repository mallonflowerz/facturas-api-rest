package com.mallonflowerz.almacen.configuration.security.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Usuario;
import com.mallonflowerz.almacen.productosYUsuarios.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> user = usuarioRepository.findByEmail(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Usuario con el email: " + username + " no existe!");
        }

        return User
                .withUsername(username)
                .password(user.get().getPassword())
                .disabled(user.get().getEnabled())
                .roles(user.get().getRol())
                .disabled(!user.get().getEnabled())
                .build();
    }

}
