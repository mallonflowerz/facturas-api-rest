package com.mallonflowerz.almacen.configuration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mallonflowerz.almacen.productosYUsuarios.models.Response;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class AuthenticationExceptionHandler {
    
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Response> handlerExpiredTokenException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new Response("El token ha caducado, inicie sesion nuevamente"));
    }
}
