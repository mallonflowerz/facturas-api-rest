package com.mallonflowerz.almacen.facturasDeVentas.models.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.*;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@JsonPropertyOrder({ "id", "nit", "razonSocial", "primerNombre",
        "segundoNombre", "primerApellido", "segundoApellido", "fechaNacimiento", "direccion",
        "ciudadResidencia", "paisResidencia" })
public class TerceroDTO {

    private UUID id;

    @NotBlank
    private String nit;

    private String razonSocial;

    private String primerNombre;

    private String segundoNombre;

    private String primerApellido;

    private String segundoApellido;

    private String direccion;

    private String ciudadResidencia;

    private String paisResidencia;

    private LocalDate fechaNacimiento;

}
