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
import com.mallonflowerz.almacen.facturasVentas.models.dto.DetalleFacturaDTO;
import com.mallonflowerz.almacen.facturasVentas.models.entity.DetalleFactura;
import com.mallonflowerz.almacen.facturasVentas.models.mapper.DetalleFacturaMapper;
import com.mallonflowerz.almacen.facturasVentas.services.DetalleFacturaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RequestMapping("/detalle")
@AllArgsConstructor
@RestController
public class DetalleController {

    private final DetalleFacturaService dfService;
    private final DetalleFacturaMapper detalleMapper;

    @GetMapping
    public ResponseEntity<Page<DetalleFactura>> listarFacturas(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<DetalleFactura> facturas = dfService.listarDetalleFacturas(pageable);
        return ResponseEntity.ok().body(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleFactura> obtenerFactura(@PathVariable UUID id) {
        Optional<DetalleFactura> factura = dfService.obtenerDetalleFacturaPorId(id);
        if (factura.isPresent()) {
            return ResponseEntity.ok().body(factura.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DetalleFactura> guardarFactura(@Valid @RequestBody DetalleFacturaDTO facturaDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            ResultError.validaciones(result);
        }
        boolean guardado = dfService.guardarDetalleFactura(detalleMapper.dtoToPojo(facturaDTO));
        if (guardado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> actualizarFactura(@PathVariable UUID id,
            @Valid @RequestBody DetalleFacturaDTO facturaDTO, BindingResult result) {
        if (result.hasErrors()) {
            ResultError.validaciones(result);
        }
        if (dfService.actualizarDetalleFactura(id, detalleMapper.dtoToPojo(facturaDTO))) {
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> eliminarFactura(@PathVariable UUID id) {
        if (dfService.eliminarDetalleFactura(id)) {
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.notFound().build();
    }
}
