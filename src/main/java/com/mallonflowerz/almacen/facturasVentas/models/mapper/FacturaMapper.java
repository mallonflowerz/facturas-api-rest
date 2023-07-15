package com.mallonflowerz.almacen.facturasVentas.models.mapper;

import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.facturasVentas.models.dto.FacturaDTO;
import com.mallonflowerz.almacen.facturasVentas.models.entity.Factura;
import com.mallonflowerz.almacen.productosYUsuarios.models.mapper.GenericMapper;

@Service
public class FacturaMapper implements GenericMapper<Factura, FacturaDTO> {

    @Override
    public Factura dtoToPojo(FacturaDTO dto) {
        Factura factura = Factura.builder()
                .id(dto.getId())
                .fechaDefactura(dto.getFechaDefactura())
                .build();
        return factura;
    }

    @Override
    public FacturaDTO pojoToDto(Factura pojo) {
        FacturaDTO factura = FacturaDTO.builder()
                .id(pojo.getId())
                .fechaDefactura(pojo.getFechaDefactura())
                .build();
        return factura;
    }

}
