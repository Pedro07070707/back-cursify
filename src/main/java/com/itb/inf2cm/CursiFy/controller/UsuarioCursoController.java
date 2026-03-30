package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.entity.UsuarioCurso;
import com.itb.inf2cm.CursiFy.model.services.UsuarioCursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarioCurso")
public class UsuarioCursoController {

    @Autowired
    private UsuarioCursoService usuarioCursoService;

    @GetMapping
    public ResponseEntity <List<UsuarioCurso>> findAll() {
        return ResponseEntity.ok(usuarioCursoService.findAll());
    }

    /*@PostMapping
    public ResponseEntity <UsuarioCurso> save(@RequestBody UsuarioCurso usuarioCurso) {
        UsuarioCurso novo = usuarioCursoService.save(usuarioCurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarUsuarioCursoPorId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(usuarioCursoService.findById(Long.parseLong(id)));
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
                            "message", "Usuário não encontrado com o id: " + id
                    )
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarUsuarioCurso(@PathVariable String id, @RequestBody UsuarioCurso usuarioCurso){
        try {
            return ResponseEntity.ok(usuarioCursoService.update(Long.parseLong(id), usuarioCurso));
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
                            "message", "Usuário não encontrado com o id: " + id
                    )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarUsuarioCurso(@PathVariable String id){
        try {
            usuarioCursoService.delete(Long.parseLong(id));
            return ResponseEntity.ok("Usuário com o id: " + id + " deletado com sucesso.");
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
                            "message", "Usuário não encontrado com o id: " + id
                    )
            );
        }
    }
}
