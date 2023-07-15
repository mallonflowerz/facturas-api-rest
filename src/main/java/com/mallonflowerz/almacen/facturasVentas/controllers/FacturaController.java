package com.mallonflowerz.almacen.facturasVentas.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mallonflowerz.almacen.configuration.validation.ResultError;
import com.mallonflowerz.almacen.facturasVentas.models.dto.FacturaDTO;
import com.mallonflowerz.almacen.facturasVentas.models.entity.Factura;
import com.mallonflowerz.almacen.facturasVentas.services.FacturaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/factura")
public class FacturaController {

    private final FacturaService facturaService;

    @GetMapping
    public ResponseEntity<Page<Factura>> listarFacturas(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Factura> facturas = facturaService.listarFacturas(pageable);
        return ResponseEntity.ok().body(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFactura(@PathVariable UUID id) {
        Optional<Factura> factura = facturaService.obtenerFacturaPorId(id);
        if (factura.isPresent()) {
            return ResponseEntity.ok().body(factura.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> guardarFactura(@Valid @RequestBody FacturaDTO facturaDTO, BindingResult result) {
        if (result.hasErrors()) {
            ResultError.validaciones(result);
        }
        if (facturaService.guardarFactura(facturaDTO)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarFactura(@PathVariable UUID id, @Valid @RequestBody FacturaDTO facturaDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            ResultError.validaciones(result);
        }
        if (facturaService.actualizarFactura(id, facturaDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> eliminarFactura(@PathVariable UUID id) {
        if (facturaService.eliminarFactura(id)) {
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.notFound().build();
    }
}
