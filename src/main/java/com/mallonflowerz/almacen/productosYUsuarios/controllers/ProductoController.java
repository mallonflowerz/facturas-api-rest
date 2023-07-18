package com.mallonflowerz.almacen.productosYUsuarios.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mallonflowerz.almacen.configuration.security.services.JwtUtilService;
import com.mallonflowerz.almacen.productosYUsuarios.models.Response;
import com.mallonflowerz.almacen.productosYUsuarios.models.dto.ProductoDTO;
import com.mallonflowerz.almacen.productosYUsuarios.models.entity.Producto;
import com.mallonflowerz.almacen.productosYUsuarios.models.mapper.ProductoMapper;
import com.mallonflowerz.almacen.productosYUsuarios.services.ProductoService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/producto")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoMapper mapper;
    private final JwtUtilService jwtUtilService;

    @GetMapping
    public ResponseEntity<Page<ProductoDTO>> listarProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size, @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        Pageable pageable = PageRequest.of(page, size);
        Page<Producto> p = productoService.listarTodosLosProductos(pageable);
        Page<ProductoDTO> pDTO = new PageImpl<>(
                p.stream().map(mapper::pojoToDto).toList());
        return ResponseEntity.ok().body(pDTO);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ProductoDTO> verProductoPorCodigo(@PathVariable String codigo,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        Optional<Producto> o = productoService.verProductoPorCodigo(codigo);
        if (o.isPresent()) {
            return ResponseEntity.ok().body(mapper.pojoToDto(o.get()));
        }
        throw new EntityNotFoundException(String.format("El producto con el codigo %s no existe", codigo));
    }

    @GetMapping("/disp/{codigo}")
    public ResponseEntity<HttpStatus> buscarCodigoDisponible(@PathVariable String codigo,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        boolean disponible = productoService.codigoDisponible(codigo);
        if (disponible) {
            return ResponseEntity.ok().build();
        }
        throw new EntityNotFoundException("El codigo indicado no existe");
    }

    @PostMapping
    public ResponseEntity<HttpStatus> crearProducto(@RequestBody ProductoDTO productoDTO,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        if (!productoService.codigoDisponible(productoDTO.getCodigo())) {
            throw new IllegalStateException();
        }
        productoService.crearProducto(mapper.dtoToPojo(productoDTO));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<HttpStatus> actualizarProducto(@RequestBody ProductoDTO productoDTO,
            @PathVariable String codigo, @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        Producto p = productoService.modificarProductoPorCodigo(codigo, mapper.dtoToPojo(productoDTO));
        if (p != null) {
            return ResponseEntity.ok().build();
        }
        throw new EntityNotFoundException("El producto indicado no existe");
    }

    @PutMapping("/cambiarDisponible/{codigo}")
    public ResponseEntity<Response> cambiarDisponible(@PathVariable String codigo,
            @RequestBody boolean disponible, @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        boolean correcto = productoService.cambiarDisponible(codigo, disponible);
        String estado = disponible ? "DISPONIBLE" : "NO DISPONIBLE";
        if (correcto) {
            return ResponseEntity.ok()
                    .body(new Response("El producto ha sido cambiado a el estado " + estado));
        }
        throw new EntityNotFoundException("El producto indicado no existe");
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<HttpStatus> eliminarProducto(@PathVariable String codigo,
            @RequestHeader("Authorization") String auth) {
        jwtUtilService.authVerification(auth);
        Optional<Producto> p = productoService.verProductoPorCodigo(codigo);
        if (p.isPresent()) {
            productoService.eliminarProductoPorId(p.get().getId());
            return ResponseEntity.ok().build();
        }
        throw new EntityNotFoundException("El producto indicado no existe");
    }

}
