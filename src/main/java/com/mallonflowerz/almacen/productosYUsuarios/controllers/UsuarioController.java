package com.mallonflowerz.almacen.productosYUsuarios.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mallonflowerz.almacen.configuration.security.services.JwtUtilService;
import com.mallonflowerz.almacen.productosYUsuarios.models.dto.UsuarioDTO;
import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Usuario;
import com.mallonflowerz.almacen.productosYUsuarios.models.mapper.UsuarioMapper;
import com.mallonflowerz.almacen.productosYUsuarios.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    private final UsuarioService userService;
    private final UsuarioMapper mapper;
    private final JwtUtilService jwtUtilService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable UUID id,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        Optional<Usuario> u = userService.obtenerUsuario(id);
        if (u.isPresent()) {
            return ResponseEntity.ok().body(mapper.pojoToDto(u.get()));
        }
        throw new EntityNotFoundException(String.format("El usuario con el id %s no existe", id));
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> mostrarTodosLosUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuarios = userService.obtenerTodosLosUsuarios(pageable);
        Page<UsuarioDTO> usersDTO = new PageImpl<>(
                usuarios.stream().map(mapper::pojoToDto).toList());

        return ResponseEntity.ok().body(usersDTO);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> guardarUsuario(@RequestBody UsuarioDTO usuarioDTO,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> actualizarUsuario(@PathVariable UUID id,
            @RequestBody UsuarioDTO usuarioDTO,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        if (userService.actualizarUsuario(id, mapper.dtoToPojo(usuarioDTO))) {
            return ResponseEntity.ok().build();
        }
        throw new EntityNotFoundException(String.format("El usuario con el id %s no existe", id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarUsuario(@PathVariable UUID id,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        if (userService.eliminarUsuario(id)) {
            return ResponseEntity.ok().build();
        }
        throw new EntityNotFoundException(String.format("El usuario con el id %s no existe", id));
    }
}
