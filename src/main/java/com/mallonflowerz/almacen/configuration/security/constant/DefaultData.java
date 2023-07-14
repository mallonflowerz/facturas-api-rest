package com.mallonflowerz.almacen.configuration.security.constant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Usuario;
import com.mallonflowerz.almacen.productosYUsuarios.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
@Transactional
public class DefaultData implements CommandLineRunner {

    private UsuarioRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            Usuario user = Usuario.builder()
                    .nombre("Admin")
                    .apellido("Administrado")
                    .email("admin@empresa.com")
                    .password(passwordEncoder.encode("12345"))
                    .telefono("0000000000")
                    .enabled(true)
                    .rol("ADMIN")
                    .build();
            userRepository.save(user);
        }
    }
}
