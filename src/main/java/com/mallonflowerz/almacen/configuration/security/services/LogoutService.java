package com.mallonflowerz.almacen.configuration.security.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LogoutService implements LogoutHandler {

    private final LoginService loginService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.replace("Bearer ", "");
                boolean eliminado = loginService.eliminarDelHistorial(token);
                if (eliminado) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write(new ObjectMapper().writeValueAsString("Logout exitoso"));
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter()
                            .write(new ObjectMapper()
                                    .writeValueAsString(
                                            "Algo salio mal, el token en la cabecera es invalido o el usuario no esta logeado"));
                }
                SecurityContextHolder.clearContext();
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(new ObjectMapper()
                        .writeValueAsString("No existe token en la cabecera"));
            }
        } catch (Exception e) {
            Map<String, String> body = new HashMap<>();
            body.put("message", "Error: Algo salio mal");
            body.put("error", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.getWriter().write(new ObjectMapper()
                        .writeValueAsString(body));
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
}
