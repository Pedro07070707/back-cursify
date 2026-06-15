package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 150, nullable = false, unique = true)
    private String email;

    @Column(name = "senha_hash", length = 255, nullable = false)
    private String senha;

    @Column(name = "role", length = 20, nullable = false)
    private String nivelAcesso;

    @Column(name = "foto_url", length = 500)
    private String fotoUrl;

    @Column(length = 500)
    private String bio;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getFotoUrl() { return fotoUrl; }

    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }

    public Boolean getAtivo() { return ativo; }

    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}
