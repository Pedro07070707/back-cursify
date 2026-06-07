package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.entity.Curso;
import com.itb.inf2cm.CursiFy.model.dto.MatriculaRequest;
import com.itb.inf2cm.CursiFy.model.dto.MatriculaResponse;
import com.itb.inf2cm.CursiFy.model.services.CursoService;
import com.itb.inf2cm.CursiFy.model.services.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;
    @Autowired
    private MatriculaService matriculaService;

    @GetMapping
    public ResponseEntity <List<Curso>> findAll() {
        return ResponseEntity.ok(cursoService.findAll());
    }

    @GetMapping("/{id}/detalhe")
    public ResponseEntity<Object> detalheCurso(@PathVariable String id, @RequestParam(required = false) Long usuarioId) {
        try {
            return ResponseEntity.ok(cursoService.detalheCurso(Long.parseLong(id), usuarioId));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "O id informado nao e valido: " + id));
        }
    }

    @PostMapping
    public ResponseEntity <Curso> save(@RequestBody Curso curso) {
        Curso novo = cursoService.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PostMapping("/{id}/matricular")
    public ResponseEntity<MatriculaResponse> matricular(@PathVariable String id, @RequestBody MatriculaRequest body) {
        Long cursoId = Long.parseLong(id);
        return ResponseEntity.ok(matriculaService.matricular(cursoId, body.getUsuarioId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarCursoPorId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(cursoService.findById(Long.parseLong(id)));
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
                            "message", "Curso não encontrado com o id: " + id
                    )
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarCurso(@PathVariable String id, @RequestBody Curso curso){
        try {
            return ResponseEntity.ok(cursoService.update(Long.parseLong(id), curso));
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
                            "message", "Curso não encontrado com o id: " + id
                    )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarCurso(@PathVariable String id){
        try {
            cursoService.delete(Long.parseLong(id));
            return ResponseEntity.ok("Curso com o id: " + id + " deletado com sucesso.");
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
                            "message", "Curso não encontrado com o id: " + id
                    )
            );
        }
    }
}
