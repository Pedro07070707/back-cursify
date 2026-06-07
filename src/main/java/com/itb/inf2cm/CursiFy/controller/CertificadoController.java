package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.dto.CertificadoResponse;
import com.itb.inf2cm.CursiFy.model.services.CertificadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certificados")
public class CertificadoController {
    @Autowired private CertificadoService certificadoService;

    @PostMapping("/curso/{cursoId}")
    public ResponseEntity<CertificadoResponse> emitir(@PathVariable Long cursoId, @RequestBody java.util.Map<String, Long> body) {
        Long usuarioId = body.get("usuarioId");
        CertificadoResponse response = certificadoService.emitirSeMatriculado(usuarioId, cursoId);
        return response == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(response);
    }
}
