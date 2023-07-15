package com.mallonflowerz.almacen.facturasVentas.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mallonflowerz.almacen.configuration.validation.ResultError;
import com.mallonflowerz.almacen.facturasVentas.models.dto.TerceroDTO;
import com.mallonflowerz.almacen.facturasVentas.models.entity.Tercero;
import com.mallonflowerz.almacen.facturasVentas.models.mapper.TerceroMapper;
import com.mallonflowerz.almacen.facturasVentas.services.TerceroService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/tercero")
public class TerceroController {

    private final TerceroService terceroService;
    private final TerceroMapper terceroMapper;

    @GetMapping
    public ResponseEntity<Page<TerceroDTO>> listarTerceros(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tercero> terceros = terceroService.obtenerTerceros(pageable);
        Page<TerceroDTO> tercerosDTO = new PageImpl<>(
                terceros.stream()
                        .map(terceroMapper::pojoToDto).toList());
        return ResponseEntity.ok().body(tercerosDTO);
    }

    @GetMapping("/{nit}")
    public ResponseEntity<TerceroDTO> mostrarTercero(@PathVariable String nit) {
        Optional<Tercero> terceroOp = terceroService.obtenerTerceroPorNit(nit);
        if (terceroOp.isPresent()) {
            return ResponseEntity.ok().body(terceroMapper.pojoToDto(terceroOp.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TerceroDTO> guardarTercero(@Valid @RequestBody TerceroDTO terceroDTO, 
    BindingResult result) {
        if (result.hasErrors()) {
            ResultError.validaciones(result);
        }
        return ResponseEntity.ok()
                .body(terceroMapper.pojoToDto(terceroService.guardarTercero(terceroMapper.dtoToPojo(terceroDTO))));
    }

    @PutMapping("/{nit}")
    public ResponseEntity<Boolean> actualizarTercero(@PathVariable String nit, 
    @Valid @RequestBody TerceroDTO terceroDTO, BindingResult result){
        if (result.hasErrors()) {
            ResultError.validaciones(result);
        }
        if (terceroService.actualizarTerceroPorNit(nit, terceroMapper.dtoToPojo(terceroDTO))){
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }

    @DeleteMapping("/{nit}")
    public ResponseEntity<Boolean> eliminarTercero(@PathVariable String nit){
        if (terceroService.eliminarTerceroPorNit(nit)){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }

}
