package com.example.pokemonapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey llave;
    private final long duracion;

    public JwtService(@Value("${app.jwt.secret}") String secreto,
                      @Value("${app.jwt.expiration-ms:3600000}") long duracion) {
        if (secreto == null || secreto.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalArgumentException("JWT_SECRET debe tener al menos 32 caracteres");
        }
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duracion del token debe ser positiva");
        }
        this.llave = Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
        this.duracion = duracion;
    }

    public String generarToken(String usuario) {
        long ahora = System.currentTimeMillis();
        return Jwts.builder()
                .subject(usuario)
                .issuedAt(new Date(ahora))
                .expiration(new Date(ahora + duracion))
                .signWith(llave)
                .compact();
    }

    public String obtenerUsuario(String token) {
        return Jwts.parser()
                .verifyWith(llave)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
