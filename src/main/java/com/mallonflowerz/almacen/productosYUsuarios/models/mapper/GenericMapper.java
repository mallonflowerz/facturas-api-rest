package com.mallonflowerz.almacen.productosYUsuarios.models.mapper;

public interface GenericMapper<P, D> {
    
    P dtoToPojo(D dto);

    D pojoToDto(P pojo);
}
