package com.mallonflowerz.almacen.facturaDeCompra.models.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Producto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonPropertyOrder({ "id", "nitTercero", "productos", "fechaDefactura" })
public class FacturaCompraDTO {
    
    private UUID id;

    @NotBlank(message = "no debe ser vacio")
    private String nitTercero;

    @NotEmpty(message = "no debe ser vacio")
    private List<Producto> productos;

    private LocalDateTime fechaDefactura;
}
