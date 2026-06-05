package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.dto.*;
import com.itb.inf2cm.CursiFy.model.entity.Usuario;
import com.itb.inf2cm.CursiFy.model.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthLoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(new ApiResponse<>(response, "Login realizado com sucesso", HttpStatus.OK.value()));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<ApiResponse<AuthResponse>> cadastro(@Valid @RequestBody AuthCadastroRequest request) {
        AuthResponse response = authService.cadastro(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(response, "Cadastro realizado com sucesso", HttpStatus.CREATED.value()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@Valid @RequestBody AuthRefreshRequest request) {
        AuthResponse response = authService.refresh(request);
        return ResponseEntity.ok(new ApiResponse<>(response, "Token renovado com sucesso", HttpStatus.OK.value()));
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<ApiResponse<Map<String, String>>> esqueciSenha(@Valid @RequestBody AuthForgotPasswordRequest request) {
        String token = authService.esqueciSenha(request);
        return ResponseEntity.ok(new ApiResponse<>(
            Map.of("resetToken", token),
            "Token de redefinicao gerado",
            HttpStatus.OK.value()
        ));
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<ApiResponse<UsuarioResponse>> redefinirSenha(@Valid @RequestBody AuthResetPasswordRequest request) {
        UsuarioResponse response = authService.redefinirSenha(request);
        return ResponseEntity.ok(new ApiResponse<>(response, "Senha redefinida com sucesso", HttpStatus.OK.value()));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UsuarioResponse>> me(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return ResponseEntity.ok(new ApiResponse<>(authService.me(authorization), "Usuario autenticado", HttpStatus.OK.value()));
    }

    @PutMapping("/perfil")
    public ResponseEntity<ApiResponse<UsuarioResponse>> updateProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody Usuario usuarioPatch
    ) {
        return ResponseEntity.ok(new ApiResponse<>(authService.updateProfile(authorization, usuarioPatch), "Perfil atualizado", HttpStatus.OK.value()));
    }

    @PutMapping("/perfil/senha")
    public ResponseEntity<ApiResponse<UsuarioResponse>> changePassword(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody Map<String, String> body
    ) {
        UsuarioResponse response = authService.changePassword(
            authorization,
            body.getOrDefault("senhaAtual", ""),
            body.getOrDefault("novaSenha", "")
        );
        return ResponseEntity.ok(new ApiResponse<>(response, "Senha atualizada", HttpStatus.OK.value()));
    }
}
