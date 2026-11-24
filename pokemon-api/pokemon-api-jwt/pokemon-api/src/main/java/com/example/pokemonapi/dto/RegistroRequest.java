package com.example.pokemonapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistroRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        @Pattern(regexp = "[A-Za-z0-9_.-]+", message = "Usuario no valido")
        String username,
        @NotBlank
        @Size(min = 8, max = 72)
        String password) {
}
