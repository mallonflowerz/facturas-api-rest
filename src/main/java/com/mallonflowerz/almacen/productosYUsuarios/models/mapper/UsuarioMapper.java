package com.mallonflowerz.almacen.productosYUsuarios.models.mapper;

import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.productosYUsuarios.models.dto.UsuarioDTO;
import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Usuario;

@Service
public class UsuarioMapper implements GenericMapper<Usuario, UsuarioDTO> {

    @Override
    public Usuario dtoToPojo(UsuarioDTO dto) {
        Usuario user = Usuario.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .telefono(dto.getTelefono())
                .enabled(dto.getEnabled())
                .build();
        return user;
    }

    @Override
    public UsuarioDTO pojoToDto(Usuario pojo) {
        UsuarioDTO user = UsuarioDTO.builder()
                .id(pojo.getId())
                .nombre(pojo.getNombre())
                .apellido(pojo.getApellido())
                .email(pojo.getEmail())
                .password(pojo.getPassword())
                .telefono(pojo.getTelefono())
                .enabled(pojo.getEnabled())
                .build();
        return user;
    }

}
