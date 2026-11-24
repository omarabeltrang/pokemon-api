package com.example.pokemonapi.service;

import com.example.pokemonapi.model.Pokemon;
import com.example.pokemonapi.repository.PokemonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PokemonService {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<Pokemon> obtenerTodos() {
        return pokemonRepository.findAll();
    }

    public Pokemon obtenerPorId(Long id) {
        return pokemonRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon no encontrado"));
    }

    public Pokemon crear(Pokemon pokemon) {
        pokemon.setId(null);
        return pokemonRepository.save(pokemon);
    }

    public Pokemon actualizar(Long id, Pokemon datos) {
        Pokemon pokemon = obtenerPorId(id);
        pokemon.setNombre(datos.getNombre());
        pokemon.setTipo(datos.getTipo());
        pokemon.setNivel(datos.getNivel());
        pokemon.setAtaque(datos.getAtaque());
        pokemon.setDefensa(datos.getDefensa());
        pokemon.setDescripcion(datos.getDescripcion());
        pokemon.setImagenUrl(datos.getImagenUrl());
        return pokemonRepository.save(pokemon);
    }

    public Pokemon actualizarParcial(Long id, ObjectNode cambios) {
        Pokemon pokemon = obtenerPorId(id);
        Pokemon datos;

        try {
            datos = objectMapper.convertValue(cambios, Pokemon.class);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos invalidos");
        }

        if (cambios.has("nombre")) {
            pokemon.setNombre(datos.getNombre());
        }
        if (cambios.has("tipo")) {
            pokemon.setTipo(datos.getTipo());
        }
        if (cambios.has("nivel")) {
            pokemon.setNivel(datos.getNivel());
        }
        if (cambios.has("ataque")) {
            pokemon.setAtaque(datos.getAtaque());
        }
        if (cambios.has("defensa")) {
            pokemon.setDefensa(datos.getDefensa());
        }
        if (cambios.has("descripcion")) {
            pokemon.setDescripcion(datos.getDescripcion());
        }
        if (cambios.has("imagenUrl")) {
            pokemon.setImagenUrl(datos.getImagenUrl());
        }

        return pokemonRepository.save(pokemon);
    }

    public void eliminar(Long id) {
        Pokemon pokemon = obtenerPorId(id);
        pokemonRepository.delete(pokemon);
    }
}
