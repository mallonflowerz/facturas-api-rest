package com.mallonflowerz.almacen.facturasVentas.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.facturasVentas.models.entity.Tercero;
import com.mallonflowerz.almacen.facturasVentas.repositories.TerceroRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TerceroService {
    
    private final TerceroRepository terceroRepository;

    public Page<Tercero> obtenerTerceros(Pageable pageable){
        return terceroRepository.findAll(pageable);
    } 

    public Optional<Tercero> obtenerTerceroPorNit(String nit){
        return terceroRepository.findByNit(nit);
    }

    public Tercero guardarTercero(Tercero tercero){
        return terceroRepository.save(tercero);
    }

    public boolean actualizarTerceroPorNit(String nit, Tercero tercero){
        Optional<Tercero> terceroOp = terceroRepository.findByNit(nit);
        if (terceroOp.isPresent()){
            tercero.setId(terceroOp.get().getId());
            terceroRepository.save(tercero);
            return true;
        }
        return false;
    }

    public boolean eliminarTerceroPorNit(String nit){
        Optional<Tercero> terceroOp = terceroRepository.findByNit(nit);
        if (terceroOp.isPresent()){
            terceroRepository.delete(terceroOp.get());
            return true;
        }
        return false;
    }

}
