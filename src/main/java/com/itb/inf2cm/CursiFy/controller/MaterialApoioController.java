package com.itb.inf2cm.CursiFy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/materialapoio")
public class MaterialApoioController {

    @Autowired
    private MaterialApoioService materialApoioService;

    @GetMapping
    public ResponseEntity <List<MaterialApoio>> findAll() {
        return ResponseEntity.ok(materialApoioService.findAll());
    }

    @PostMapping
    public ResponseEntity <MaterialApoio> save(@RequestBody MaterialApoio materialApoio) {
        MaterialApoio novo = materialApoioService.save(materialApoio);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarMaterialApoioPorId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(materialApoioService.findById(Long.parseLong(id)));
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
                            "message", "Material de apoio não encontrado com o id: " + id
                    )
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarMaterialApoio(@PathVariable String id, @RequestBody MaterialApoio materialApoio){
        try {
            return ResponseEntity.ok(materialApoioService.update(Long.parseLong(id), materialApoio));
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
                            "message", "Material de apoio não encontrado com o id: " + id
                    )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarMaterialApoio(@PathVariable String id){
        try {
            materialApoioService.delete(Long.parseLong(id));
            return ResponseEntity.ok("Material de apoio com o id: " + id + " deletado com sucesso.");
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
                            "message", "Material de apoio não encontrado com o id: " + id
                    )
            );
        }
    }
}
