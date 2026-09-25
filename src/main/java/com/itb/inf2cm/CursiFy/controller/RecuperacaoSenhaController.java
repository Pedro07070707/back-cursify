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

    @PostMapping("/perfil/solicitar-codigo")
    public ResponseEntity<Map<String, String>> solicitarCodigoPerfil(@RequestBody Map<String, Long> body) {
        recuperacaoSenhaService.enviarCodigoPerfil(body.get("usuarioId"));
        return ResponseEntity.ok(Map.of("message", "Código enviado para o e-mail cadastrado."));
    }

    @PostMapping("/perfil/confirmar")
    public ResponseEntity<Map<String, String>> confirmarCodigoPerfil(@RequestBody Map<String, Object> body) {
        recuperacaoSenhaService.validarCodigoPerfil(Long.valueOf(String.valueOf(body.get("usuarioId"))), String.valueOf(body.get("codigo")));
        return ResponseEntity.ok(Map.of("message", "Código confirmado."));
    }
}
