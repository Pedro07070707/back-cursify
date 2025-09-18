package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.entity.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity <List<Curso>> findAll() {
        return ResponseEntity.ok(cursoService.findAll());
    }

    @PostMapping
    public ResponseEntity <Curso> save(@RequestBody Curso curso) {
        Curso novo = cursoService.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }
}
