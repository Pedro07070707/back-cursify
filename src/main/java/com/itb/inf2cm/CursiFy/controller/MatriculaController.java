package com.itb.inf2cm.CursiFy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/matricula")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @GetMapping
    public ResponseEntity <List<Matricula>> findAll() {
        return ResponseEntity.ok(matriculaService.findAll());
    }

    @PostMapping
    public ResponseEntity <Matricula> save(@RequestBody Matricula matricula) {
        Matricula novo = matriculaService.save(matricula);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarMatriculaPorId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(matriculaService.findById(Long.parseLong(id)));
        }
        catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "O id informado não é válido: " + id
                    )
            );
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", "Matrícula não encontrada com o id: " + id
                    )
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarMatricula(@PathVariable String id, @RequestBody Matricula matricula){
        try {
            return ResponseEntity.ok(matriculaService.update(Long.parseLong(id), matricula));
        }
        catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "O id informado não é válido: " + id
                    )
            );
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", "Matrícula não encontrada com o id: " + id
                    )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarMatricula(@PathVariable String id){
        try {
            matriculaService.delete(Long.parseLong(id));
            return ResponseEntity.ok("Matrícula com o id: " + id + " deletada com sucesso.");
        }
        catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "O id informado não é válido: " + id
                    )
            );
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", "Matrícula não encontrada com o id: " + id
                    )
            );
        }
    }
}
