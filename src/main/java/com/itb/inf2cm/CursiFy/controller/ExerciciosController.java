package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.entity.Exercicios;
import com.itb.inf2cm.CursiFy.model.services.ExerciciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercicios")
public class ExerciciosController {

    @Autowired
    private ExerciciosService exerciciosService;

    @GetMapping
    public ResponseEntity<List<Exercicios>> findAll() {
        return ResponseEntity.ok(exerciciosService.findAll());
    }

    @PostMapping
    public ResponseEntity<Exercicios> save(@RequestBody Exercicios exercicios) {
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciciosService.save(exercicios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercicios> findById(@PathVariable Long id) {
        return ResponseEntity.ok(exerciciosService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercicios> update(@PathVariable Long id, @RequestBody Exercicios exercicios) {
        return ResponseEntity.ok(exerciciosService.update(id, exercicios));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        exerciciosService.delete(id);
        return ResponseEntity.ok("Exercicio com o id: " + id + " deletado com sucesso.");
    }
}
