package com.mallonflowerz.almacen.facturasDeVentas.models.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DetalleFacturaDTO {

    private UUID id;

    private String codigo;
    
    @NotBlank(message = "no debe ser vacio")
    private String nombreProducto;

    private BigDecimal cantidad;

}
