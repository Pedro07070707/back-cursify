package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.dto.PublicTrackResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trilhas")
public class TrilhasController {

    @Autowired
    private PublicTrackController publicTrackController;

    @GetMapping
    public ResponseEntity<List<PublicTrackResponse>> list() {
        return ResponseEntity.ok(publicTrackController.list(null));
    }

    @GetMapping("/usuario/progresso")
    public ResponseEntity<List<PublicTrackResponse>> progressoUsuario() {
        return ResponseEntity.ok(publicTrackController.list(null));
    }
}
