package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.entity.Chat;
import com.itb.inf2cm.CursiFy.model.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity <List<Chat>> findAll() {
        return ResponseEntity.ok(chatService.findAll());
    }

    @PostMapping
    public ResponseEntity <Chat> save(@RequestBody Chat chat) {
        Chat novo = chatService.save(chat);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarChatPorId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(chatService.findById(Long.parseLong(id)));
        }
        catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "O id informado não é válido: " + id
                    )
            );
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", "Chat não encontrado com o id: " + id
                    )
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarChat(@PathVariable String id, @RequestBody Chat chat){
        try {
            return ResponseEntity.ok(chatService.update(Long.parseLong(id), chat));
        }
        catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "O id informado não é válido: " + id
                    )
            );
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", "Chat não encontrado com o id: " + id
                    )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarChat(@PathVariable String id){
        try {
            chatService.delete(Long.parseLong(id));
            return ResponseEntity.ok("Chat com o id: " + id + " deletado com sucesso.");
        }
        catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "O id informado não é válido: " + id
                    )
            );
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", "Chat não encontrado com o id: " + id
                    )
            );
        }
    }
}
