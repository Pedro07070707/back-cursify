package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.entity.Usuario;
import com.itb.inf2cm.CursiFy.model.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity <List<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PostMapping
    public ResponseEntity <Usuario> save(@RequestBody Usuario usuario) {
        Usuario novo = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarUsuarioPorId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(usuarioService.findById(Long.parseLong(id)));
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
    public ResponseEntity<Object> atualizarCurso(@PathVariable String id, @RequestBody Usuario usuario){
        try {
            return ResponseEntity.ok(usuarioService.update(Long.parseLong(id), usuario));
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
    public ResponseEntity<Object> deletarUsuario(@PathVariable String id){
        try {
            usuarioService.delete(Long.parseLong(id));
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
