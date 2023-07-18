package com.mallonflowerz.almacen.facturasDeVentas.controllers;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mallonflowerz.almacen.configuration.security.services.JwtUtilService;
import com.mallonflowerz.almacen.configuration.validation.ResultError;
import com.mallonflowerz.almacen.facturasDeVentas.models.dto.TerceroDTO;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Tercero;
import com.mallonflowerz.almacen.facturasDeVentas.models.mapper.TerceroMapper;
import com.mallonflowerz.almacen.facturasDeVentas.services.TerceroService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/tercero")
public class TerceroController {

    private final TerceroService terceroService;
    private final TerceroMapper terceroMapper;
    private final JwtUtilService jwtUtilService;

    @GetMapping
    public ResponseEntity<Page<TerceroDTO>> listarTerceros(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size, @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        Pageable pageable = PageRequest.of(page, size);
        Page<Tercero> terceros = terceroService.obtenerTerceros(pageable);
        Page<TerceroDTO> tercerosDTO = new PageImpl<>(
                terceros.stream()
                        .map(terceroMapper::pojoToDto).toList());
        return ResponseEntity.ok().body(tercerosDTO);
    }

    @GetMapping("/{nit}")
    public ResponseEntity<TerceroDTO> mostrarTercero(@PathVariable String nit,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        Optional<Tercero> terceroOp = terceroService.obtenerTerceroPorNit(nit);
        if (terceroOp.isPresent()) {
            return ResponseEntity.ok().body(terceroMapper.pojoToDto(terceroOp.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<HttpStatus> guardarTercero(@Valid @RequestBody TerceroDTO terceroDTO,
            BindingResult result, @RequestHeader("Authorization") String auth) {
        if (result.hasErrors()) {
            ResultError.validaciones(result);
        }
        jwtUtilService.authVerification(auth);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{nit}")
    public ResponseEntity<HttpStatus> actualizarTercero(@PathVariable String nit,
            @Valid @RequestBody TerceroDTO terceroDTO, BindingResult result,
            @RequestHeader("Authorization") String auth) {
        if (result.hasErrors()) {
            ResultError.validaciones(result);
        }
        jwtUtilService.authVerification(auth);
        if (terceroService.actualizarTerceroPorNit(nit, terceroMapper.dtoToPojo(terceroDTO))) {
            return ResponseEntity.ok().build();
        }
        throw new EntityNotFoundException("No existe el tercero con el NIT " + nit);
    }

    @DeleteMapping("/{nit}")
    public ResponseEntity<HttpStatus> eliminarTercero(@PathVariable String nit,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        if (terceroService.eliminarTerceroPorNit(nit)) {
            return ResponseEntity.ok().build();
        }
        throw new EntityNotFoundException("No existe el tercero con el NIT " + nit);
    }

}
