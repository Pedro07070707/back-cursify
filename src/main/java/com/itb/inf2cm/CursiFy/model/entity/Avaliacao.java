package com.itb.inf2cm.CursiFy.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "Avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long numeroAvaliacao;

    @Column(length = 500, nullable = false)
    private String enunciadoAvaliacao;

    @Column(length = 100, nullable = false)
    private String alternativaAvaliacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"senha", "email", "dataCadastro", "statusUsuario", "nivelAcesso"})
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Column(length = 3, nullable = false)
    private int statusAvaliacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroAvaliacao() {
        return numeroAvaliacao;
    }

    public void setNumeroAvaliacao(Long numeroAvaliacao) {
        this.numeroAvaliacao = numeroAvaliacao;
    }

    public String getEnunciadoAvaliacao() {
        return enunciadoAvaliacao;
    }

    public void setEnunciadoAvaliacao(String enunciadoAvaliacao) {
        this.enunciadoAvaliacao = enunciadoAvaliacao;
    }

    public String getAlternativaAvaliacao() {
        return alternativaAvaliacao;
    }

    public void setAlternativaAvaliacao(String alternativaAvaliacao) {
        this.alternativaAvaliacao = alternativaAvaliacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public int getStatusAvaliacao() {
        return statusAvaliacao;
    }

    public void setStatusAvaliacao(int statusAvaliacao) {
        this.statusAvaliacao = statusAvaliacao;
    }
}
