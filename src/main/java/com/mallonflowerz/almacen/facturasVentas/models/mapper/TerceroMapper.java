package com.mallonflowerz.almacen.facturasVentas.models.mapper;

import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.facturasVentas.models.dto.TerceroDTO;
import com.mallonflowerz.almacen.facturasVentas.models.entity.Tercero;
import com.mallonflowerz.almacen.productosYUsuarios.models.mapper.GenericMapper;

@Service
public class TerceroMapper implements GenericMapper<Tercero, TerceroDTO> {

    @Override
    public Tercero dtoToPojo(TerceroDTO dto) {
        Tercero tercero = Tercero.builder()
                .id(dto.getId())
                .nit(dto.getNit())
                .razonSocial(dto.getRazonSocial())
                .primerNombre(dto.getPrimerNombre())
                .segundoNombre(dto.getSegundoNombre())
                .primerApellido(dto.getPrimerApellido())
                .segundoApellido(dto.getSegundoApellido())
                .direccion(dto.getDireccion())
                .ciudadResidencia(dto.getCiudadResidencia())
                .paisResidencia(dto.getPaisResidencia())
                .fechaNacimiento(dto.getFechaNacimiento())
                .build();
        return tercero;
    }

    @Override
    public TerceroDTO pojoToDto(Tercero pojo) {
        TerceroDTO terceroDTO = TerceroDTO.builder()
                .id(pojo.getId())
                .nit(pojo.getNit())
                .razonSocial(pojo.getRazonSocial())
                .primerNombre(pojo.getPrimerNombre())
                .segundoNombre(pojo.getSegundoNombre())
                .primerApellido(pojo.getPrimerApellido())
                .segundoApellido(pojo.getSegundoApellido())
                .direccion(pojo.getDireccion())
                .ciudadResidencia(pojo.getCiudadResidencia())
                .paisResidencia(pojo.getPaisResidencia())
                .fechaNacimiento(pojo.getFechaNacimiento())
                .build();
        return terceroDTO;
    }

}
