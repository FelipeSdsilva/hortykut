package com.github.hortykut.hortykut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.github.hortykut.hortykut.model.Categoria;
import com.github.hortykut.hortykut.repository.CategoriaRepository;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Categoria>> getAll() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable Long id) {
        return categoriaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Categoria>> getByTitle(@PathVariable String descricao) {
        return ResponseEntity.ok(categoriaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
    }

    @PostMapping
    public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
    }

    @PutMapping
    public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria) {
        return categoriaRepository.findById(categoria.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        if (categoria.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        categoriaRepository.deleteById(id);
    }
}
