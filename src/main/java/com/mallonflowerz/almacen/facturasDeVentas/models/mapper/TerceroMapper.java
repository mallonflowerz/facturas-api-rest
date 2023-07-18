package com.mallonflowerz.almacen.facturasDeVentas.models.mapper;

import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.facturasDeVentas.models.dto.TerceroDTO;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Tercero;
import com.mallonflowerz.almacen.productosYUsuarios.models.mapper.GenericMapper;

@Service
public class TerceroMapper implements GenericMapper<Tercero, TerceroDTO> {

    @Override
    public Tercero dtoToPojo(TerceroDTO dto) {
        Tercero tercero = Tercero.builder()
                .id(dto.getId())
                .nit(dto.getNit())
                .razonSocial(dto.getRazonSocial().toUpperCase())
                .primerNombre(dto.getPrimerNombre() != null ? dto.getPrimerNombre().toUpperCase() : null)
                .segundoNombre(dto.getSegundoNombre() != null ? dto.getSegundoNombre().toUpperCase() : null)
                .primerApellido(dto.getPrimerApellido() != null ? dto.getPrimerApellido().toUpperCase() : null)
                .segundoApellido(dto.getSegundoApellido() != null ? dto.getSegundoApellido().toUpperCase() : null)
                .direccion(dto.getDireccion() != null ? dto.getDireccion().toUpperCase() : null)
                .ciudadResidencia(dto.getCiudadResidencia() != null ? dto.getCiudadResidencia().toUpperCase() : null)
                .paisResidencia(dto.getPaisResidencia() != null ? dto.getPaisResidencia().toUpperCase() : null)
                .fechaNacimiento(dto.getFechaNacimiento())
                .build();
        return tercero;
    }

    @Override
    public TerceroDTO pojoToDto(Tercero pojo) {
        TerceroDTO terceroDTO = TerceroDTO.builder()
                .id(pojo.getId())
                .nit(pojo.getNit())
                .razonSocial(pojo.getRazonSocial().toUpperCase())
                .primerNombre(pojo.getPrimerNombre() != null ? pojo.getPrimerNombre().toUpperCase() : null)
                .segundoNombre(pojo.getSegundoNombre() != null ? pojo.getSegundoNombre().toUpperCase() : null)
                .primerApellido(pojo.getPrimerApellido() != null ? pojo.getPrimerApellido().toUpperCase() : null)
                .segundoApellido(pojo.getSegundoApellido() != null ? pojo.getSegundoApellido().toUpperCase() : null)
                .direccion(pojo.getDireccion() != null ? pojo.getDireccion().toUpperCase() : null)
                .ciudadResidencia(pojo.getCiudadResidencia() != null ? pojo.getCiudadResidencia().toUpperCase() : null)
                .paisResidencia(pojo.getPaisResidencia() != null ? pojo.getPaisResidencia().toUpperCase() : null)
                .fechaNacimiento(pojo.getFechaNacimiento())
                .build();
        return terceroDTO;
    }

}
