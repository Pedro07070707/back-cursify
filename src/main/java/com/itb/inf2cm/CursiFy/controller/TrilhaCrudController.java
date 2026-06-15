package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.entity.NoTrilha;
import com.itb.inf2cm.CursiFy.model.entity.Trilha;
import com.itb.inf2cm.CursiFy.model.services.TrilhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/trilhas")
public class TrilhaCrudController {

    @Autowired
    private TrilhaService trilhaService;

    @GetMapping
    public ResponseEntity<List<Trilha>> findAll() {
        return ResponseEntity.ok(trilhaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(trilhaService.findById(Long.parseLong(id)));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("status", 400, "error", "Bad Request", "message", "Id invalido: " + id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("status", 404, "error", "Not Found", "message", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Trilha> save(@RequestBody Trilha trilha) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trilhaService.save(trilha));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody Trilha trilha) {
        try {
            return ResponseEntity.ok(trilhaService.update(Long.parseLong(id), trilha));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("status", 400, "error", "Bad Request", "message", "Id invalido: " + id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("status", 404, "error", "Not Found", "message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        try {
            trilhaService.delete(Long.parseLong(id));
            return ResponseEntity.ok(Map.of("message", "Trilha removida com sucesso."));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("status", 400, "error", "Bad Request", "message", "Id invalido: " + id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("status", 404, "error", "Not Found", "message", e.getMessage()));
        }
    }

    @GetMapping("/{trilhaId}/nos")
    public ResponseEntity<Object> listNos(@PathVariable String trilhaId) {
        try {
            return ResponseEntity.ok(trilhaService.findById(Long.parseLong(trilhaId)).getNos());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("status", 400, "error", "Bad Request", "message", "Id invalido: " + trilhaId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("status", 404, "error", "Not Found", "message", e.getMessage()));
        }
    }

    @PostMapping("/{trilhaId}/nos")
    public ResponseEntity<Object> saveNo(@PathVariable String trilhaId, @RequestBody NoTrilha noTrilha) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(trilhaService.saveNo(Long.parseLong(trilhaId), noTrilha));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("status", 400, "error", "Bad Request", "message", "Id invalido: " + trilhaId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("status", 404, "error", "Not Found", "message", e.getMessage()));
        }
    }

    @PutMapping("/nos/{noId}")
    public ResponseEntity<Object> updateNo(@PathVariable String noId, @RequestBody NoTrilha noTrilha) {
        try {
            return ResponseEntity.ok(trilhaService.updateNo(Long.parseLong(noId), noTrilha));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("status", 400, "error", "Bad Request", "message", "Id invalido: " + noId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("status", 404, "error", "Not Found", "message", e.getMessage()));
        }
    }

    @DeleteMapping("/nos/{noId}")
    public ResponseEntity<Object> deleteNo(@PathVariable String noId) {
        try {
            trilhaService.deleteNo(Long.parseLong(noId));
            return ResponseEntity.ok(Map.of("message", "No removido com sucesso."));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("status", 400, "error", "Bad Request", "message", "Id invalido: " + noId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("status", 404, "error", "Not Found", "message", e.getMessage()));
        }
    }
}
