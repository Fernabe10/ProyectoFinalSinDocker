package com.fgr.apirest.security;

import com.fgr.apirest.entity.User;
import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Se encarga de generar y validar tokens JWT cuando un usuario inicia sesión.
 */
@Component
public class JwtTokenProvider {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${app.security.jwt.secret}")
    private String jwtSecret;

    @Value("${app.security.jwt.expiration}")
    private Long jwtExpirationMs;

    /**
     * Genera un token JWT usando los detalles del usuario
     */
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(user.getUsername()) // Usamos el nombre de usuario como subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs * 1000)) // Expiración en segundos
                .signWith(SignatureAlgorithm.HS256, jwtSecret) // Firma con algoritmo HMAC y el secret
                .claim("username", user.getUsername()) // Añadimos claims adicionales si es necesario
                .claim("role", user.getRole()) // Por ejemplo, agregamos el rol
                .compact();
    }

    /**
     * Verifica si el token es válido
     */
    public boolean isValidToken(String token) {
        if (!StringUtils.hasLength(token)) {
            return false;
        }

        try {
            Jwts.parserBuilder()
                .setSigningKey(jwtSecret) // Usamos el mismo secret para la validación
                .build()
                .parseClaimsJws(token); // Si el token es válido, esto no lanza excepciones
            return true;
        } catch (SignatureException e) {
            log.info("Error en la firma del token", e);
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            log.info("Token incorrecto", e);
        } catch (ExpiredJwtException e) {
            log.info("Token expirado", e);
        }
        return false;
    }

    /**
     * Extrae el nombre de usuario desde el token
     */
    public String getUsernameFromToken(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(jwtSecret) // Usamos el mismo secret para parsear
                .build();

        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.get("username").toString(); // Traemos el username desde el claim
    }
}
