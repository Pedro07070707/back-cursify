package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.entity.Usuario;
import com.itb.inf2cm.CursiFy.model.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.update(id, usuario));
    }

    @PutMapping("/{id}/tema")
    public ResponseEntity<Map<String, String>> updateTheme(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Usuario usuario = usuarioService.findById(id);
        usuario.setTemaPreferido(body.get("temaPreferido"));
        usuarioService.saveTheme(usuario);
        return ResponseEntity.ok(Map.of("temaPreferido", usuario.getTemaPreferido()));
    }

    @PutMapping("/{id}/perfil")
    public ResponseEntity<Usuario> updateProfile(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Usuario usuario = usuarioService.findById(id);
        if (body.get("nome") != null) usuario.setNome(String.valueOf(body.get("nome")));
        if (body.get("bio") != null) usuario.setBio(String.valueOf(body.get("bio")));
        if (body.get("temaPreferido") != null) usuario.setTemaPreferido(String.valueOf(body.get("temaPreferido")));
        if (body.get("foto") != null && !String.valueOf(body.get("foto")).isBlank()) usuario.setFoto(Base64.getDecoder().decode(String.valueOf(body.get("foto"))));
        if (body.get("fotoCapa") != null && !String.valueOf(body.get("fotoCapa")).isBlank()) usuario.setFotoCapa(Base64.getDecoder().decode(String.valueOf(body.get("fotoCapa"))));
        return ResponseEntity.ok(usuarioService.saveTheme(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.ok("Usuário com o id: " + id + " deletado com sucesso.");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> credentials) {
        Optional<Usuario> usuario = usuarioService.login(credentials.get("email"), credentials.get("senha"));
        if (usuario.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Email ou senha incorretos."));
        }
        Usuario u = usuario.get();
        if (!"Ativo".equals(u.getStatusUsuario())) {
            return ResponseEntity.status(403).body(Map.of("message", "Sua conta foi desativada. Entre em contato com o administrador."));
        }
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("id", u.getId());
        resposta.put("nome", u.getNome());
        resposta.put("email", u.getEmail());
        resposta.put("cpf", u.getCpf());
        resposta.put("nivelAcesso", u.getNivelAcesso());
        resposta.put("professorAprovado", u.getProfessorAprovado());
        resposta.put("bio", u.getBio());
        resposta.put("foto", u.getFoto());
        resposta.put("fotoCapa", u.getFotoCapa());
        resposta.put("temaPreferido", u.getTemaPreferido());
        return ResponseEntity.ok(resposta);
    }
}
