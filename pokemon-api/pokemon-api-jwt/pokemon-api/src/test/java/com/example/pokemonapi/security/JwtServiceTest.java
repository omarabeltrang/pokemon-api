package com.example.pokemonapi.security;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtServiceTest {

    private static final String SECRET = "clave-de-prueba-de-mas-de-treinta-y-dos-caracteres-123";

    @Test
    void generaYValidaToken() {
        JwtService service = new JwtService(SECRET, 60000);
        String token = service.generarToken("ash");
        assertEquals("ash", service.obtenerUsuario(token));
    }

    @Test
    void rechazaTokenModificado() {
        JwtService service = new JwtService(SECRET, 60000);
        String[] partes = service.generarToken("misty").split("\\.");
        String modificado = partes[0] + "." + partes[1] + "." + "AAAA";
        assertThrows(JwtException.class, () -> service.obtenerUsuario(modificado));
    }

    @Test
    void rechazaClaveDemasiadoCorta() {
        assertThrows(IllegalArgumentException.class, () -> new JwtService("corta", 60000));
    }
}
