package com.mallonflowerz.almacen.productosYUsuarios.models.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.*;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@JsonPropertyOrder({ "id", "nombre", "apellido", "telefono", "email", "password", "enabled" })
public class UsuarioDTO {

    private UUID id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    private String telefono;

    @Email
    @NotBlank
    private String email;

    @JsonIgnore
    @NotBlank
    private String password;

    @NotBlank
    private Boolean enabled;

}
