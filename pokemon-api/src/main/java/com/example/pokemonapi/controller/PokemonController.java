package com.example.pokemonapi.controller;

import com.example.pokemonapi.model.Pokemon;
import com.example.pokemonapi.service.PokemonService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pokemones")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping
    public List<Pokemon> obtenerTodos() {
        return pokemonService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Pokemon obtenerPorId(@PathVariable Long id) {
        return pokemonService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pokemon crear(@RequestBody Pokemon pokemon) {
        return pokemonService.crear(pokemon);
    }

    @PutMapping("/{id}")
    public Pokemon actualizar(@PathVariable Long id, @RequestBody Pokemon pokemon) {
        return pokemonService.actualizar(id, pokemon);
    }

    @PatchMapping("/{id}")
    public Pokemon actualizarParcial(@PathVariable Long id, @RequestBody ObjectNode cambios) {
        return pokemonService.actualizarParcial(id, cambios);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        pokemonService.eliminar(id);
    }
}
