package com.mallonflowerz.almacen.facturasDeVentas.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
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
import com.mallonflowerz.almacen.facturasDeVentas.models.dto.FacturaDTO;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.Factura;
import com.mallonflowerz.almacen.facturasDeVentas.services.FacturaService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/factura/venta")
public class FacturaController {

    private final FacturaService facturaService;
    private final JwtUtilService jwtUtilService;

    @GetMapping
    public ResponseEntity<Page<Factura>> listarFacturas(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size, @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        Pageable pageable = PageRequest.of(page, size);
        Page<Factura> facturas = facturaService.listarFacturas(pageable);
        return ResponseEntity.ok().body(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFactura(@PathVariable UUID id,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        Optional<Factura> factura = facturaService.obtenerFacturaPorId(id);
        if (factura.isPresent()) {
            return ResponseEntity.ok().body(factura.get());
        }
        throw new EntityNotFoundException("No existe la factura con el id " + id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> guardarFactura(@Valid @RequestBody FacturaDTO facturaDTO, BindingResult result,
            @RequestHeader("Authorization") String auth) {
        if (result.hasErrors()) {
            ResultError.validaciones(result);
        }
        jwtUtilService.authVerification(auth);
        if (facturaService.guardarFactura(facturaDTO)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> actualizarFactura(@PathVariable UUID id, @Valid @RequestBody FacturaDTO facturaDTO,
            BindingResult result, @RequestHeader("Authorization") String auth) {
        if (result.hasErrors()) {
            ResultError.validaciones(result);
        }
        jwtUtilService.authVerification(auth);
        if (facturaService.actualizarFactura(id, facturaDTO)) {
            return ResponseEntity.ok().build();
        }
        throw new EntityNotFoundException(String.format("La factura con el id %s no existe", id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarFactura(@PathVariable UUID id,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        if (facturaService.eliminarFactura(id)) {
            return ResponseEntity.ok().build();
        }
        throw new EntityNotFoundException(String.format("La factura con el id %s no existe", id));
    }
}
