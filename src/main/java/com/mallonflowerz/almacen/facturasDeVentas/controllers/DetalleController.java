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
import com.mallonflowerz.almacen.facturasDeVentas.models.dto.DetalleFacturaDTO;
import com.mallonflowerz.almacen.facturasDeVentas.models.entity.DetalleFactura;
import com.mallonflowerz.almacen.facturasDeVentas.models.mapper.DetalleFacturaMapper;
import com.mallonflowerz.almacen.facturasDeVentas.services.DetalleFacturaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RequestMapping("/api/v1/detalle")
@AllArgsConstructor
@RestController
public class DetalleController {

    private final DetalleFacturaService dfService;
    private final DetalleFacturaMapper detalleMapper;
    private final JwtUtilService jwtUtilService;

    @GetMapping
    public ResponseEntity<Page<DetalleFactura>> listarFacturas(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size, @RequestHeader("Authorization") String auth) {

        jwtUtilService.authVerification(auth);
        Pageable pageable = PageRequest.of(page, size);
        Page<DetalleFactura> facturas = dfService.listarDetalleFacturas(pageable);
        return ResponseEntity.ok().body(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleFactura> obtenerFactura(@PathVariable UUID id,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        Optional<DetalleFactura> factura = dfService.obtenerDetalleFacturaPorId(id);
        if (factura.isPresent()) {
            return ResponseEntity.ok().body(factura.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DetalleFactura> guardarFactura(@Valid @RequestBody DetalleFacturaDTO facturaDTO,
            BindingResult result, @RequestHeader("Authorization") String auth) {
        if (result.hasErrors()) {
            ResultError.validaciones(result);
        }
        jwtUtilService.authVerification(auth);
        boolean guardado = dfService.guardarDetalleFactura(detalleMapper.dtoToPojo(facturaDTO));
        if (guardado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> actualizarFactura(@PathVariable UUID id,
            @Valid @RequestBody DetalleFacturaDTO facturaDTO, BindingResult result,
            @RequestHeader("Authorization") String auth) {
        if (result.hasErrors()) {
            ResultError.validaciones(result);
        }
        jwtUtilService.authVerification(auth);
        if (dfService.actualizarDetalleFactura(id, detalleMapper.dtoToPojo(facturaDTO))) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarFactura(@PathVariable UUID id,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        if (dfService.eliminarDetalleFactura(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
