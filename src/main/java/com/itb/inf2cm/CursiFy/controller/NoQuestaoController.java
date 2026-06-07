package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.dto.NoQuestaoResponse;
import com.itb.inf2cm.CursiFy.model.services.NoQuestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nos")
public class NoQuestaoController {
    @Autowired private NoQuestaoService noQuestaoService;

    @GetMapping("/{noId}/questoes")
    public ResponseEntity<List<NoQuestaoResponse>> listarQuestoes(@PathVariable Long noId) {
        return ResponseEntity.ok(noQuestaoService.listarPorNo(noId));
    }
}
