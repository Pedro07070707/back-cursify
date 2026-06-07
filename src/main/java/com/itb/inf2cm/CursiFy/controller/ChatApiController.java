package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.dto.ChatConversationResponse;
import com.itb.inf2cm.CursiFy.model.dto.ChatMessageResponse;
import com.itb.inf2cm.CursiFy.model.services.ChatV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatApiController {
    @Autowired private ChatV2Service chatV2Service;

    @GetMapping("/conversas")
    public ResponseEntity<List<ChatConversationResponse>> conversas() {
        return ResponseEntity.ok(chatV2Service.findConversations());
    }

    @GetMapping("/conversas/{id}/mensagens")
    public ResponseEntity<List<ChatMessageResponse>> mensagens(@PathVariable Long id) {
        return ResponseEntity.ok(chatV2Service.findMessages(id));
    }

    @PostMapping("/conversas")
    public ResponseEntity<ChatConversationResponse> criarOuRetornar(@RequestBody Map<String, Long> body) {
        Long destinatarioId = body.get("destinatarioId");
        ChatConversationResponse response = new ChatConversationResponse(null, "DIRETO", null, "Conversa " + destinatarioId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/conversas/{id}/mensagens")
    public ResponseEntity<ChatMessageResponse> enviar(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Long remetenteId = Long.valueOf(String.valueOf(body.get("remetenteId")));
        String conteudo = String.valueOf(body.get("conteudo"));
        String tipo = body.get("tipo") == null ? "TEXTO" : String.valueOf(body.get("tipo"));
        return ResponseEntity.ok(chatV2Service.sendMessage(id, remetenteId, conteudo, tipo));
    }
}
