package com.mallonflowerz.almacen.productosYUsuarios.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mallonflowerz.almacen.configuration.security.model.UserRequest;
import com.mallonflowerz.almacen.configuration.security.services.JwtUtilService;
import com.mallonflowerz.almacen.configuration.security.services.LoginService;
import com.mallonflowerz.almacen.productosYUsuarios.models.Response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtUtilService jwtUtilService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authManager;
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Response> autenticarse(@RequestBody UserRequest user) {
        String email = user.getEmail();
        loginService.guardarEnHistorial(email);

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, user.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        String token = jwtUtilService.generateToken(userDetails);

        return ResponseEntity.ok().body(new Response(token));
    }
}
