package com.mallonflowerz.almacen.facturaDeCompra.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mallonflowerz.almacen.facturaDeCompra.models.dto.FacturaCompraDTO;
import com.mallonflowerz.almacen.facturaDeCompra.models.entity.FacturaCompra;
import com.mallonflowerz.almacen.facturaDeCompra.services.FacturaCompraService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/api/v1/factura/compra")
@RestController
public class FacturaCompraController {

    private final FacturaCompraService facturaCompraService;

    @GetMapping
    public ResponseEntity<Page<FacturaCompra>> listarFacturas(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FacturaCompra> facturas = facturaCompraService.obtenerFacturas(pageable);
        return ResponseEntity.ok().body(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaCompra> mostrarFactura(@PathVariable UUID id) {
        Optional<FacturaCompra> factura = facturaCompraService.obtenerFacturaPorId(id);
        if (factura.isPresent()) {
            return ResponseEntity.ok().body(factura.get());
        }
        throw new EntityNotFoundException("No existe la factura con el id " + id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> guardarFactura(@RequestBody FacturaCompraDTO facturaCompraDTO) {
        if (facturaCompraService.guardarFactura(facturaCompraDTO)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<HttpStatus> actualizarFactura(@RequestBody FacturaCompraDTO facturaCompraDTO,
            UUID id) {
        if (facturaCompraService.actualizarFactura(facturaCompraDTO, id)) {
            return ResponseEntity.ok().build();
        }
        throw new EntityNotFoundException("No existe la factura con el id " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarFactura(@PathVariable UUID id) {
        if (facturaCompraService.eliminarFactura(id)) {
            return ResponseEntity.ok().build();
        }
        throw new EntityNotFoundException("No existe la factura con el id " + id);
    }
}
