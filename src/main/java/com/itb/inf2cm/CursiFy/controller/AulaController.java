package com.itb.inf2cm.CursiFy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/aula")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @GetMapping
    public ResponseEntity <List<Aula>> findAll() {
        return ResponseEntity.ok(aulaService.findAll());
    }

    @PostMapping
    public ResponseEntity <Aula> save(@RequestBody Aula aula) {
        Aula novo = aulaService.save(aula);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarAulaPorId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(aulaService.findById(Long.parseLong(id)));
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
                            "message", "Aula não encontrada com o id: " + id
                    )
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarAula(@PathVariable String id, @RequestBody Aula aula){
        try {
            return ResponseEntity.ok(aulaService.update(Long.parseLong(id), aula));
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
                            "message", "Aula não encontrada com o id: " + id
                    )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarAula(@PathVariable String id){
        try {
            aulaService.delete(Long.parseLong(id));
            return ResponseEntity.ok("Aula com o id: " + id + " deletada com sucesso.");
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
                            "message", "Aula não encontrada com o id: " + id
                    )
            );
        }
    }
}
