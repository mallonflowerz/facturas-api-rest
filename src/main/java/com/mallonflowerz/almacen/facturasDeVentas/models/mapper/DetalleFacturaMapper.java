package com.mallonflowerz.almacen.facturasDeVentas.models.mapper;

import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.facturasDeVentas.models.dto.DetalleFacturaDTO;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.DetalleFactura;
import com.mallonflowerz.almacen.productosYUsuarios.models.mapper.GenericMapper;

@Service
public class DetalleFacturaMapper implements GenericMapper<DetalleFactura, DetalleFacturaDTO> {

    @Override
    public DetalleFactura dtoToPojo(DetalleFacturaDTO dto) {
        DetalleFactura detalle = DetalleFactura.builder()
                .id(dto.getId())
                .nombreProducto(dto.getNombreProducto())
                .cantidad(dto.getCantidad())
                .build();

        return detalle;
    }

    @Override
    public DetalleFacturaDTO pojoToDto(DetalleFactura pojo) {
        DetalleFacturaDTO detalle = DetalleFacturaDTO.builder()
                .id(pojo.getId())
                .nombreProducto(pojo.getNombreProducto())
                .cantidad(pojo.getCantidad())
                .build();

        return detalle;
    }

}
