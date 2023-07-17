package com.mallonflowerz.almacen.facturasDeVentas.models.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@JsonPropertyOrder({ "id", "nitTercero", "detalles", "fechaDefactura" })
public class FacturaDTO {

    private UUID id;

    @NotBlank(message = "no debe ser vacio")
    private String nitTercero;

    @NotEmpty(message = "no debe ser vacio")
    private List<UUID> detalles;

    private LocalDateTime fechaDefactura;

}
