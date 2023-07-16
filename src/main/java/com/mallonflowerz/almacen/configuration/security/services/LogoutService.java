package com.mallonflowerz.almacen.configuration.security.services;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

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
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            boolean eliminado = loginService.eliminarDelHistorial(token);
            if (eliminado) {
                response.setStatus(HttpServletResponse.SC_OK);
                try {
                    response.getWriter().write("Logout exitoso");
                    SecurityContextHolder.clearContext();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                try {
                    response.getWriter()
                            .write("Algo salio mal, el token en la cabecera es invalido o el usuario no esta logeado");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.getWriter().write("No existe token en la cabecera");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
