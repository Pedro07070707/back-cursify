package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Usuario")
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String senha;

    @Column(length = 11, nullable = false)
    private String cpf;

    @Column(length = 10, nullable = true)
    private String nivelAcesso;

    @Lob
    @Column(nullable = true)
    private byte[] foto;

    @Column(length = 2000)
    private String bio;

    @Lob
    @Column(name = "foto_capa")
    private byte[] fotoCapa;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    @Column(length = 20, nullable = false)
    private String statusUsuario;

    @Column(name = "professor_aprovado", nullable = false, columnDefinition = "TINYINT NOT NULL")
    private Integer professorAprovado = 0;

    @Column(name = "tema_preferido", length = 10, nullable = false)
    private String temaPreferido = "light";

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public byte[] getFoto() { return foto; }

    public void setFoto(byte[] foto) { this.foto = foto; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public byte[] getFotoCapa() { return fotoCapa; }
    public void setFotoCapa(byte[] fotoCapa) { this.fotoCapa = fotoCapa; }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getStatusUsuario() {
        return statusUsuario;
    }

    public void setStatusUsuario(String statusUsuario) {
        this.statusUsuario = statusUsuario;
    }

    public Integer getProfessorAprovado() {
        return professorAprovado;
    }

    public void setProfessorAprovado(Integer professorAprovado) {
        this.professorAprovado = professorAprovado == null ? 0 : professorAprovado;
    }
    public String getTemaPreferido() { return temaPreferido; }
    public void setTemaPreferido(String temaPreferido) { this.temaPreferido = "dark".equalsIgnoreCase(temaPreferido) ? "dark" : "light"; }
}
