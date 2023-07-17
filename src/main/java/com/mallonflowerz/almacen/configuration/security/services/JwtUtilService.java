package com.mallonflowerz.almacen.configuration.security.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.configuration.security.repositories.HistoryLoginsRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@AllArgsConstructor
@Service
public class JwtUtilService {

    private final HistoryLoginsRepository hlRepo;

    // LLAVE_MUY_SECRETA => [Base64] => TExBVkVfTVVZX1NFQ1JFVEE=
    private static final String JWT_SECRET_KEY = "TExBVkVfTVVZX1NFQ1JFVEE=";

    public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * (long) 8; // 8 Horas

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Agregando informacion adicional como "claim"
        String email = userDetails.getUsername();
        var rol = userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0);
        claims.put("rol", rol);
        claims.put("email", email);
        return createToken(claims, userDetails.getUsername());
    }

    public void authVerification(String auth) {
        String email = this.extractClaim(this.quitarBearer(auth), Claims::getSubject);
        if (!hlRepo.findByEmail(email).isPresent()) {
            throw new EntityNotFoundException(
                    "No esta logeado o el token ha expirado, inicie sesion nuevamente");
        }
    }

    private String quitarBearer(String token) {
        return token.replace("Bearer ", "");
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
