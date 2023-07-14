package com.mallonflowerz.almacen.productosYUsuarios.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mallonflowerz.almacen.productosYUsuarios.models.Response;
import com.mallonflowerz.almacen.productosYUsuarios.models.dto.UsuarioDTO;
import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Usuario;
import com.mallonflowerz.almacen.productosYUsuarios.models.mapper.UsuarioMapper;
import com.mallonflowerz.almacen.productosYUsuarios.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService userService;
    private final UsuarioMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable UUID id) {
        Optional<Usuario> u = userService.obtenerUsuario(id);
        if (u.isPresent()) {
            return ResponseEntity.ok().body(mapper.pojoToDto(u.get()));
        }
        throw new EntityNotFoundException();
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> mostrarTodosLosUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuarios = userService.obtenerTodosLosUsuarios(pageable);
        Page<UsuarioDTO> usersDTO = new PageImpl<>(
                usuarios.stream().map(mapper::pojoToDto).toList());

        return ResponseEntity.ok().body(usersDTO);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> guardarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok().body(
                mapper.pojoToDto(userService.guardarUsuario(mapper.dtoToPojo(usuarioDTO))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> actualizarUsuario(@PathVariable UUID id, @RequestBody UsuarioDTO usuarioDTO) {
        if (userService.actualizarUsuario(id, mapper.dtoToPojo(usuarioDTO))) {
            return ResponseEntity.ok().body(new Response("Usuario actualizado con existe"));
        }
        throw new EntityNotFoundException();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> eliminarUsuario(@PathVariable UUID id) {
        if (userService.eliminarUsuario(id)) {
            return ResponseEntity.ok().body(new Response("Usuario eliminado con exito"));
        }
        throw new EntityNotFoundException();
    }
}
