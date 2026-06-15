package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.dto.ApiResponse;
import com.itb.inf2cm.CursiFy.model.dto.UsuarioResponse;
import com.itb.inf2cm.CursiFy.model.entity.Usuario;
import com.itb.inf2cm.CursiFy.model.repository.UsuarioRepository;
import com.itb.inf2cm.CursiFy.model.services.AuthService;
import com.itb.inf2cm.CursiFy.model.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosApiController {
    private final AuthService authService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public UsuariosApiController(AuthService authService, UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.authService = authService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/perfil")
    public ResponseEntity<ApiResponse<UsuarioResponse>> perfil(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return ResponseEntity.ok(new ApiResponse<>(authService.me(authorization), "Perfil carregado", HttpStatus.OK.value()));
    }

    @PutMapping("/perfil")
    public ResponseEntity<ApiResponse<UsuarioResponse>> atualizarPerfil(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody Usuario usuarioPatch
    ) {
        return ResponseEntity.ok(new ApiResponse<>(authService.updateProfile(authorization, usuarioPatch), "Perfil atualizado", HttpStatus.OK.value()));
    }

    @PutMapping("/perfil/senha")
    public ResponseEntity<ApiResponse<UsuarioResponse>> alterarSenha(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody Map<String, String> body
    ) {
        return ResponseEntity.ok(new ApiResponse<>(
                authService.changePassword(
                        authorization,
                        body.getOrDefault("senhaAtual", ""),
                        body.getOrDefault("novaSenha", "")
                ),
                "Senha atualizada",
                HttpStatus.OK.value()
        ));
    }

    @GetMapping("/admin/usuarios")
    public ResponseEntity<ApiResponse<Page<UsuarioResponse>>> listar(Pageable pageable) {
        Page<UsuarioResponse> data = usuarioRepository.findAll(pageable).map(UsuarioResponse::new);
        return ResponseEntity.ok(new ApiResponse<>(data, "Usuarios listados", HttpStatus.OK.value()));
    }

    @PutMapping("/admin/usuarios/{id}/status")
    public ResponseEntity<ApiResponse<UsuarioResponse>> alterarStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Usuario usuario = usuarioService.findById(id);
        String status = body.getOrDefault("statusUsuario", Boolean.TRUE.equals(usuario.getAtivo()) ? "Ativo" : "Inativo");
        usuario.setAtivo(!"Inativo".equalsIgnoreCase(status));
        return ResponseEntity.ok(new ApiResponse<>(new UsuarioResponse(usuarioService.saveRaw(usuario)), "Status atualizado", HttpStatus.OK.value()));
    }

    @PutMapping("/admin/usuarios/{id}/role")
    public ResponseEntity<ApiResponse<UsuarioResponse>> alterarRole(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Usuario usuario = usuarioService.findById(id);
        usuario.setNivelAcesso(body.getOrDefault("nivelAcesso", usuario.getNivelAcesso()));
        return ResponseEntity.ok(new ApiResponse<>(new UsuarioResponse(usuarioService.saveRaw(usuario)), "Role atualizada", HttpStatus.OK.value()));
    }

    @GetMapping("/{id}/online")
    public ResponseEntity<ApiResponse<Map<String, Object>>> online(@PathVariable Long id) {
        usuarioService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(
                Map.of(
                        "online", Boolean.FALSE,
                        "ultimoAcesso", LocalDateTime.now().toString()
                ),
                "Status online consultado",
                HttpStatus.OK.value()
        ));
    }
}
