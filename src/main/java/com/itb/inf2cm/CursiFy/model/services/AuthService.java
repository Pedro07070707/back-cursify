package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.auth.JwtService;
import com.itb.inf2cm.CursiFy.model.auth.TokenStore;
import com.itb.inf2cm.CursiFy.model.dto.*;
import com.itb.inf2cm.CursiFy.model.entity.Usuario;
import com.itb.inf2cm.CursiFy.model.exception.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenStore tokenStore;

    public AuthService(UsuarioService usuarioService, PasswordEncoder passwordEncoder, JwtService jwtService, TokenStore tokenStore) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenStore = tokenStore;
    }

    public AuthResponse login(AuthLoginRequest request) {
        Usuario usuario = usuarioService.findByEmail(request.getEmail());
        validateActiveUser(usuario);
        if (!matches(request.getSenha(), usuario.getSenha())) {
            throw new BadRequestException("Usuario ou senha invalidos");
        }
        return buildAuthResponse(usuario);
    }

    public AuthResponse cadastro(AuthCadastroRequest request) {
        String nome = request.getNome() == null ? null : request.getNome().trim();
        String email = request.getEmail() == null ? null : request.getEmail().trim().toLowerCase();
        String senha = request.getSenha();
        String role = request.getRole() == null ? "USUARIO" : request.getRole().trim().toUpperCase();

        if (usuarioService.findAll().stream().anyMatch(user -> email != null && email.equalsIgnoreCase(user.getEmail()))) {
            throw new BadRequestException("Ja existe um usuario com esse email");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setNivelAcesso(role);
        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setAtivo(true);
        Usuario salvo = usuarioService.save(usuario);
        return buildAuthResponse(salvo);
    }

    public AuthResponse refresh(AuthRefreshRequest request) {
        Long userId = tokenStore.validateRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new BadRequestException("Refresh token invalido"))
                .getUserId();
        Usuario usuario = usuarioService.findById(userId);
        validateActiveUser(usuario);
        return buildAuthResponse(usuario);
    }

    public String esqueciSenha(AuthForgotPasswordRequest request) {
        Usuario usuario = usuarioService.findByEmail(request.getEmail());
        String token = tokenStore.createResetToken(usuario.getId());
        return token;
    }

    public UsuarioResponse redefinirSenha(AuthResetPasswordRequest request) {
        Long userId = tokenStore.validateResetToken(request.getToken())
                .orElseThrow(() -> new BadRequestException("Token de redefinicao invalido"))
                .getUserId();
        Usuario usuario = usuarioService.findById(userId);
        usuario.setSenha(passwordEncoder.encode(request.getNovaSenha()));
        Usuario salvo = usuarioService.saveRaw(usuario);
        tokenStore.revokeResetToken(request.getToken());
        return new UsuarioResponse(salvo);
    }

    public UsuarioResponse me(String authorizationHeader) {
        Usuario usuario = resolveUserFromAuthorization(authorizationHeader);
        validateActiveUser(usuario);
        return new UsuarioResponse(usuario);
    }

    public UsuarioResponse updateProfile(String authorizationHeader, Usuario usuarioPatch) {
        Usuario usuario = resolveUserFromAuthorization(authorizationHeader);
        Usuario atualizado = usuarioService.updateProfile(usuario.getId(), usuarioPatch);
        return new UsuarioResponse(atualizado);
    }

    public UsuarioResponse changePassword(String authorizationHeader, String senhaAtual, String novaSenha) {
        Usuario usuario = resolveUserFromAuthorization(authorizationHeader);
        Usuario atualizado = usuarioService.changePassword(usuario.getId(), senhaAtual, novaSenha);
        return new UsuarioResponse(atualizado);
    }

    private AuthResponse buildAuthResponse(Usuario usuario) {
        String accessToken = jwtService.createAccessToken(usuario.getId(), usuario.getEmail(), usuario.getNivelAcesso());
        String refreshToken = tokenStore.createRefreshToken(usuario.getId());
        return new AuthResponse(new UsuarioResponse(usuario), accessToken, refreshToken);
    }

    private boolean matches(String rawPassword, String storedPassword) {
        return passwordEncoder.matches(rawPassword, storedPassword) || rawPassword.equals(storedPassword);
    }

    private void validateActiveUser(Usuario usuario) {
        if (Boolean.FALSE.equals(usuario.getAtivo())) {
            throw new BadRequestException("Usuario desativado");
        }
    }

    private Usuario resolveUserFromAuthorization(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Token ausente");
        }
        String token = authorizationHeader.substring(7);
        try {
            return usuarioService.findById(jwtService.validateAndExtractUserId(token));
        } catch (RuntimeException exception) {
            throw new BadRequestException("Token invalido");
        }
    }
}
