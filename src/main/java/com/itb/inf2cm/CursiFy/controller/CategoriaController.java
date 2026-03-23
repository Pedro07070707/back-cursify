package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity <List<Categoria>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @PostMapping
    public ResponseEntity <Categoria> save(@RequestBody Categoria categoria) {
        Categoria novo = categoriaService.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarCategoriaPorId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(categoriaService.findById(Long.parseLong(id)));
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
                            "message", "Categoria não encontrada com o id: " + id
                    )
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarCategoria(@PathVariable String id, @RequestBody Categoria categoria){
        try {
            return ResponseEntity.ok(categoriaService.update(Long.parseLong(id), categoria));
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
                            "message", "Categoria não encontrada com o id: " + id
                    )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarCategoria(@PathVariable String id){
        try {
            categoriaService.delete(Long.parseLong(id));
            return ResponseEntity.ok("Categoria com o id: " + id + " deletada com sucesso.");
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
                            "message", "Categoria não encontrada com o id: " + id
                    )
            );
        }
    }
}
