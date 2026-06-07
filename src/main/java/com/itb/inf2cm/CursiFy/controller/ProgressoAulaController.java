package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.dto.ConcluirAulaRequest;
import com.itb.inf2cm.CursiFy.model.dto.ConcluirAulaResponse;
import com.itb.inf2cm.CursiFy.model.services.ProgressoAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aulas")
public class ProgressoAulaController {
    @Autowired private ProgressoAulaService progressoAulaService;

    @PostMapping("/{aulaId}/concluir")
    public ResponseEntity<ConcluirAulaResponse> concluirAula(@PathVariable Long aulaId, @RequestBody ConcluirAulaRequest body) {
        return ResponseEntity.ok(progressoAulaService.concluirAula(aulaId, body.getUsuarioId(), body.getCursoId()));
    }
}
