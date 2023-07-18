package com.mallonflowerz.almacen.configuration.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mallonflowerz.almacen.configuration.security.services.JwtUtilService;
import com.mallonflowerz.almacen.configuration.security.services.LogoutService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtilService jwtUtilService;
    private final UserDetailsService detailsService;
    private final LogoutService logoutService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        Map<String, String> body = new HashMap<>();

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtUtilService.extractUsername(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.detailsService.loadUserByUsername(username);

                if (jwtUtilService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken uo = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());

                    uo.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(uo);
                }
            }
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            if (e.getClass().equals(ExpiredJwtException.class)) {
                body.put("message", "Error: El token ha expirado, inicie sesion nuevamente");
                body.put("error", e.getMessage());
            } else if (e.getClass().equals(JwtException.class)) {
                body.put("message", "Error: El token no es valido");
                body.put("error", e.getMessage());
            }
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(401);
            response.setContentType("application/json");
            logoutService.logout(request, response, null);
        }
    }

}
