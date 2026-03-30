package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.entity.UsuarioChat;
import com.itb.inf2cm.CursiFy.model.services.UsuarioChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarioChat")
public class UsuarioChatController {

    @Autowired
    private UsuarioChatService usuarioChatService;

    @GetMapping
    public ResponseEntity <List<UsuarioChat>> findAll() {
        return ResponseEntity.ok(usuarioChatService.findAll());
    }

    /*@PostMapping
    public ResponseEntity <UsuarioChat> save(@RequestBody UsuarioChat usuarioChat) {
        UsuarioChat novo = usuarioChatService.save(usuarioChat);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarUsuarioChatPorId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(usuarioChatService.findById(Long.parseLong(id)));
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
                            "message", "Usuário não encontrado com o id: " + id
                    )
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarUsuarioChat(@PathVariable String id, @RequestBody UsuarioChat usuarioChat){
        try {
            return ResponseEntity.ok(usuarioChatService.update(Long.parseLong(id), usuarioChat));
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
                            "message", "Usuário não encontrado com o id: " + id
                    )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarUsuarioChat(@PathVariable String id){
        try {
            usuarioChatService.delete(Long.parseLong(id));
            return ResponseEntity.ok("Usuário com o id: " + id + " deletado com sucesso.");
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
                            "message", "Usuário não encontrado com o id: " + id
                    )
            );
        }
    }
}
