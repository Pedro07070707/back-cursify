package com.itb.inf2cm.CursiFy.model.dto;

import com.itb.inf2cm.CursiFy.model.entity.Usuario;

import java.time.LocalDateTime;

public class UsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private String nivelAcesso;
    private LocalDateTime dataCadastro;
    private String statusUsuario;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.nivelAcesso = usuario.getNivelAcesso();
        this.dataCadastro = usuario.getDataCadastro();
        this.statusUsuario = Boolean.FALSE.equals(usuario.getAtivo()) ? "Inativo" : "Ativo";
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getNivelAcesso() { return nivelAcesso; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public String getStatusUsuario() { return statusUsuario; }
}
