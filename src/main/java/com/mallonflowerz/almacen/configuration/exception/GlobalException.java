package com.mallonflowerz.almacen.configuration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.mallonflowerz.almacen.productosYUsuarios.models.Response;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handlerAllError(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Response("Exception: " + ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Response> handlerCodigoNotAviliable(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response("Error: " + ex.getMessage()));
    }

    @ExceptionHandler(ExcesoCantidadException.class)
    public ResponseEntity<Response> handlerCantidadException(ExcesoCantidadException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response("Error: " + ex.getMessage()));
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<Response> handlerInternalServerError(InternalServerError ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Response("Internal Error: " + ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Response> handlerUserNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(new Response("Error: " + ex.getMessage()));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Response> handlerUserExits(EntityExistsException ex) {
        return ResponseEntity.status(400).body(new Response("Error: " + ex.getMessage()));
    }

}
