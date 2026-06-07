package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.dto.AvaliarProjetoRequest;
import com.itb.inf2cm.CursiFy.model.dto.AvaliarProjetoResponse;
import com.itb.inf2cm.CursiFy.model.dto.ProjetoEnviadoRequest;
import com.itb.inf2cm.CursiFy.model.dto.ProjetoEnviadoResponse;
import com.itb.inf2cm.CursiFy.model.services.ProjetoEnviadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {
    @Autowired private ProjetoEnviadoService projetoEnviadoService;

    @PostMapping("/nos/{noId}/enviar")
    public ResponseEntity<ProjetoEnviadoResponse> enviarProjeto(@PathVariable Long noId, @RequestBody ProjetoEnviadoRequest body) {
        return ResponseEntity.ok(projetoEnviadoService.enviar(noId, body));
    }

    @GetMapping("/nos/{noId}/ultimo")
    public ResponseEntity<ProjetoEnviadoResponse> ultimoProjeto(@PathVariable Long noId, @RequestParam Long usuarioId) {
        ProjetoEnviadoResponse response = projetoEnviadoService.listarUltimo(noId, usuarioId);
        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<ProjetoEnviadoResponse>> listarPendentes() {
        return ResponseEntity.ok(projetoEnviadoService.listarPendentes());
    }

    @PostMapping("/{projetoId}/avaliar")
    public ResponseEntity<AvaliarProjetoResponse> avaliarProjeto(@PathVariable Long projetoId, @RequestBody AvaliarProjetoRequest body) {
        return ResponseEntity.ok(projetoEnviadoService.avaliar(projetoId, body));
    }
}
