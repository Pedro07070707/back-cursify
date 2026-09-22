package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.entity.Material;
import com.itb.inf2cm.CursiFy.model.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping
    public ResponseEntity<List<Material>> findAll() {
        return ResponseEntity.ok(materialService.findAll());
    }

    @PostMapping
    public ResponseEntity<Material> save(@RequestBody Material material) {
        return ResponseEntity.status(HttpStatus.CREATED).body(materialService.save(material));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> findById(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Material> update(@PathVariable Long id, @RequestBody Material material) {
        return ResponseEntity.ok(materialService.update(id, material));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        materialService.delete(id);
        return ResponseEntity.ok("Material com o id: " + id + " deletado com sucesso.");
    }
}
