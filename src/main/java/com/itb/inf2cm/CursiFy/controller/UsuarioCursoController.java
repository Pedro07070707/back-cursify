package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.entity.Curso;
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
    public ResponseEntity<List<UsuarioCurso>> findAll() {
        return ResponseEntity.ok(usuarioCursoService.findAll());
    }

    @GetMapping("/professor/{usuarioId}")
    public ResponseEntity<List<Curso>> findCursosByProfessor(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioCursoService.findCursosByProfessor(usuarioId));
    }

    @GetMapping("/progresso/{usuarioId}/{cursoId}")
    public ResponseEntity<UsuarioCurso> progresso(@PathVariable Long usuarioId, @PathVariable Long cursoId) {
        return ResponseEntity.ok(usuarioCursoService.getProgress(usuarioId, cursoId));
    }

    @PutMapping("/progresso/{usuarioId}/{cursoId}")
    public ResponseEntity<Map<String, Object>> salvarProgresso(@PathVariable Long usuarioId, @PathVariable Long cursoId, @RequestBody UsuarioCurso dados) {
        UsuarioCurso salvo = usuarioCursoService.saveProgress(usuarioId, cursoId, dados);
        return ResponseEntity.ok(Map.of(
                "id", salvo.getId(),
                "usuarioId", usuarioId,
                "cursoId", cursoId,
                "progresso", salvo.getProgresso(),
                "concluido", Boolean.TRUE.equals(salvo.getConcluido())
        ));
    }

    @PostMapping("/inscrever/{usuarioId}/{cursoId}")
    public ResponseEntity<Map<String, Object>> inscrever(@PathVariable Long usuarioId, @PathVariable Long cursoId) {
        UsuarioCurso salvo = usuarioCursoService.enroll(usuarioId, cursoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "id", salvo.getId(),
                "usuarioId", usuarioId,
                "cursoId", cursoId,
                "progresso", salvo.getProgresso()
        ));
    }

    @DeleteMapping("/inscrever/{usuarioId}/{cursoId}")
    public ResponseEntity<Void> removerInscricao(@PathVariable Long usuarioId, @PathVariable Long cursoId) {
        usuarioCursoService.removeEnrollment(usuarioId, cursoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ocupacao/{cursoId}")
    public ResponseEntity<Map<String, Object>> ocupacao(@PathVariable Long cursoId) {
        long matriculados = usuarioCursoService.countStudents(cursoId);
        return ResponseEntity.ok(Map.of("matriculados", matriculados, "limite", 100, "cheio", matriculados >= 100));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioCurso> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioCursoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioCurso> update(@PathVariable Long id, @RequestBody UsuarioCurso usuarioCurso) {
        return ResponseEntity.ok(usuarioCursoService.update(id, usuarioCurso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        usuarioCursoService.delete(id);
        return ResponseEntity.ok("UsuarioCurso com o id: " + id + " deletado com sucesso.");
    }
}
