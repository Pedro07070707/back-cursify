package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.dto.ConcluirNoResponse;
import com.itb.inf2cm.CursiFy.model.dto.ResponderNoRequest;
import com.itb.inf2cm.CursiFy.model.dto.ResponderNoResponse;
import com.itb.inf2cm.CursiFy.model.services.ProgressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/progresso")
public class ProgressoController {
    @Autowired private ProgressoService progressoService;

    @PostMapping("/nos/{noId}/concluir")
    public ResponseEntity<ConcluirNoResponse> concluirNo(@PathVariable Long noId, @RequestBody Map<String, Long> body) {
        Long usuarioId = body.get("usuarioId");
        return ResponseEntity.ok(progressoService.concluirNo(usuarioId, noId));
    }

    @PostMapping("/nos/{noId}/responder")
    public ResponseEntity<ResponderNoResponse> responderNo(@PathVariable Long noId, @RequestBody ResponderNoRequest body) {
        return ResponseEntity.ok(progressoService.responderNo(noId, body));
    }
}
