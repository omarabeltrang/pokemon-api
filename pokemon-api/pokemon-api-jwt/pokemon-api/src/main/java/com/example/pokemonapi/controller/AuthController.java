package com.example.pokemonapi.controller;

import com.example.pokemonapi.dto.LoginRequest;
import com.example.pokemonapi.dto.RegistroRequest;
import com.example.pokemonapi.dto.TokenResponse;
import com.example.pokemonapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping({"/register", "/registro"})
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse registrar(@Valid @RequestBody RegistroRequest datos) {
        return authService.registrar(datos);
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest datos) {
        return authService.login(datos);
    }
}
