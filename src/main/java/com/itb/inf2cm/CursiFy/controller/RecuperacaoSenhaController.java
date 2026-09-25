package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.services.RecuperacaoSenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/recuperacao-senha")
public class RecuperacaoSenhaController {

    @Autowired
    private RecuperacaoSenhaService recuperacaoSenhaService;

    @PostMapping("/solicitar")
    public ResponseEntity<Map<String, String>> solicitar(@RequestBody Map<String, String> body) {
        try {
            recuperacaoSenhaService.solicitarRecuperacao(body.get("email"));
        } catch (Exception ignored) {
            // Não revela se o e-mail existe ou não
        }
        return ResponseEntity.ok(Map.of("message", "Se este e-mail estiver cadastrado, as instruções foram enviadas."));
    }

    @GetMapping("/validar")
    public ResponseEntity<Map<String, String>> validar(@RequestParam String token) {
        recuperacaoSenhaService.validarToken(token);
        return ResponseEntity.ok(Map.of("message", "Token válido."));
    }

    @PostMapping("/redefinir")
    public ResponseEntity<Map<String, String>> redefinir(@RequestBody Map<String, String> body) {
        recuperacaoSenhaService.redefinirSenha(body.get("token"), body.get("novaSenha"));
        return ResponseEntity.ok(Map.of("message", "Senha redefinida com sucesso."));
    }
}
