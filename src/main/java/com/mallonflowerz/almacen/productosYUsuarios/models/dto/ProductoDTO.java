package com.mallonflowerz.almacen.productosYUsuarios.models.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@JsonPropertyOrder({ "id", "codigo", "nombreProducto", "descripcion", "cantidad", "valorCompra", "valorVenta", "disponible", "fechaCreacion",
        "fechaModificacion" })
public class ProductoDTO {

    @JsonProperty("id")
    @JsonAlias("idProducto")
    private UUID id;

    @JsonProperty(value = "codigo", required = false)
    @JsonAlias("codigoProducto")
    private String codigo;

    @NotBlank
    @JsonProperty(value = "nombreProducto", required = true)
    @JsonAlias("nombreProducto")
    private String nombreProducto;

    @JsonProperty(value = "descripcion", required = false)
    @JsonAlias("descripcionProducto")
    private String descripcion;

    @NotBlank
    @JsonProperty(value = "cantidad", required = true)
    @JsonAlias("cantidadProducto")
    private BigDecimal cantidad;

    @NotBlank
    @JsonProperty(value = "valorCompra", required = true)
    @JsonAlias({ "coste", "precioDeCompra", "valorDeCompra" })
    private BigDecimal valorCompra;

    @NotBlank
    @JsonProperty(value = "valorVenta", required = true)
    @JsonAlias({ "precioDeCVenta", "valorDeVenta" })
    private BigDecimal valorVenta;

    @NotBlank
    @JsonProperty(value = "disponible", required = true)
    @JsonAlias("enLote")
    private Boolean disponible;

    private boolean exederLote;

    @JsonProperty(value = "fechaCreacion", required = false)
    @JsonAlias("fechaDeCreacion")
    private LocalDateTime fechaCreacion;

    @JsonProperty(value = "fechaModificacion", required = false)
    @JsonAlias({ "fechaDeModificacion", "ultimaModificacion" })
    private LocalDateTime fechaModificacion;
}
