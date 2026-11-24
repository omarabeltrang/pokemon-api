package com.example.pokemonapi.security;

import com.example.pokemonapi.repository.UsuarioRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UsuarioRepository usuarioRepository) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        String encabezado = request.getHeader("Authorization");
        if (encabezado != null) {
            if (!encabezado.startsWith("Bearer ") || encabezado.length() <= 7) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Encabezado de autorizacion invalido");
                return;
            }
            try {
                String username = jwtService.obtenerUsuario(encabezado.substring(7).trim());
                var usuario = usuarioRepository.findByUsername(username);
                if (usuario.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario no encontrado");
                    return;
                }
                var auth = new UsernamePasswordAuthenticationToken(
                        usuario.get().getUsername(), null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER")));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException | IllegalArgumentException ex) {
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalido o expirado");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
