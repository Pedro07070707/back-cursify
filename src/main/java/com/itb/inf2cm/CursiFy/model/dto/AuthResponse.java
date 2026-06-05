package com.itb.inf2cm.CursiFy.model.dto;

public class AuthResponse {
    private UsuarioResponse usuario;
    private String accessToken;
    private String refreshToken;

    public AuthResponse(UsuarioResponse usuario, String accessToken, String refreshToken) {
        this.usuario = usuario;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public UsuarioResponse getUsuario() { return usuario; }
    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
}
