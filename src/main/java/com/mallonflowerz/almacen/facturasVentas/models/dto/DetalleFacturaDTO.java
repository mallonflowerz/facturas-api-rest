package com.mallonflowerz.almacen.facturasVentas.models.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DetalleFacturaDTO {

    private UUID id;
    
    @NotBlank(message = "no debe ser vacio")
    private String nombreProducto;

    private BigDecimal cantidad;

}
