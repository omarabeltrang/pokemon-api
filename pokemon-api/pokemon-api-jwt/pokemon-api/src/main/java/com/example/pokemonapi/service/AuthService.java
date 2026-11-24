package com.example.pokemonapi.service;

import com.example.pokemonapi.dto.LoginRequest;
import com.example.pokemonapi.dto.RegistroRequest;
import com.example.pokemonapi.dto.TokenResponse;
import com.example.pokemonapi.model.Usuario;
import com.example.pokemonapi.repository.UsuarioRepository;
import com.example.pokemonapi.security.JwtService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public TokenResponse registrar(RegistroRequest datos) {
        String username = datos.username().trim();
        if (usuarioRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPasswordHash(passwordEncoder.encode(datos.password()));
        try {
            usuarioRepository.saveAndFlush(usuario);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe");
        }
        return new TokenResponse(jwtService.generarToken(username), "Bearer");
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest datos) {
        Usuario usuario = usuarioRepository.findByUsername(datos.username())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Usuario o contrasena incorrectos"));

        if (!passwordEncoder.matches(datos.password(), usuario.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Usuario o contrasena incorrectos");
        }
        return new TokenResponse(jwtService.generarToken(usuario.getUsername()), "Bearer");
    }
}
