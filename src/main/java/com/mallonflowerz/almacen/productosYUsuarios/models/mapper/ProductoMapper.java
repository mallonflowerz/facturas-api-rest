package com.mallonflowerz.almacen.productosYUsuarios.models.mapper;

import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.productosYUsuarios.models.dto.ProductoDTO;
import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Producto;

@Service
public class ProductoMapper implements GenericMapper<Producto, ProductoDTO> {

    @Override
    public Producto dtoToPojo(ProductoDTO dto) {
        Producto p = Producto.builder()
                .id(dto.getId())
                .codigo(dto.getCodigo())
                .nombreProducto(dto.getNombreProducto().toUpperCase())
                .descripcion(dto.getDescripcion())
                .cantidad(dto.getCantidad())
                .valorCompra(dto.getValorCompra())
                .valorVenta(dto.getValorVenta())
                .disponible(dto.getDisponible())
                .fechaCreacion(dto.getFechaCreacion())
                .fechaModificacion(dto.getFechaModificacion())
                .build();

        return p;
    }

    @Override
    public ProductoDTO pojoToDto(Producto pojo) {
        ProductoDTO pDTO = ProductoDTO.builder()
                .id(pojo.getId())
                .codigo(pojo.getCodigo())
                .nombreProducto(pojo.getNombreProducto())
                .descripcion(pojo.getDescripcion())
                .cantidad(pojo.getCantidad())
                .valorCompra(pojo.getValorCompra())
                .valorVenta(pojo.getValorVenta())
                .disponible(pojo.getDisponible())
                .fechaCreacion(pojo.getFechaCreacion())
                .fechaModificacion(pojo.getFechaModificacion())
                .build();

        return pDTO;
    }
}
