package com.itb.inf2cm.CursiFy.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Exercicios")
public class Exercicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String titulo;

    @Column(length = 100, nullable = false)
    private String subtitulo;

    @Column(length = 500, nullable = false)
    private String conteudo;

    @Column(length = 2000, nullable = false)
    private String enunciado;

    @ElementCollection
    @CollectionTable(name = "Exercicios_alternativas", joinColumns = @JoinColumn(name = "exercicio_id"))
    @Column(name = "alternativa", length = 500, nullable = false)
    private List<String> alternativas = new ArrayList<>();

    @Column(length = 500, nullable = false)
    private String respostaCorreta;

    @Column(length = 2000)
    private String explicacao;

    @Column(nullable = false)
    private Integer pontos = 1;

    @Column(length = 200, nullable = true)
    private String link;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"senha", "cpf", "email", "dataCadastro", "statusUsuario", "nivelAcesso"})
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Column(length = 20, nullable = false)
    private String statusExercicios = "Nao concluido";

    @PrePersist
    @PreUpdate
    private void garantirStatus() {
        if (statusExercicios == null || statusExercicios.isBlank()) {
            statusExercicios = "Nao concluido";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }
    public List<String> getAlternativas() { return alternativas; }
    public void setAlternativas(List<String> alternativas) { this.alternativas = alternativas; }
    public String getRespostaCorreta() { return respostaCorreta; }
    public void setRespostaCorreta(String respostaCorreta) { this.respostaCorreta = respostaCorreta; }
    public String getExplicacao() { return explicacao; }
    public void setExplicacao(String explicacao) { this.explicacao = explicacao; }
    public Integer getPontos() { return pontos; }
    public void setPontos(Integer pontos) { this.pontos = pontos; }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getStatusExercicios() {
        return statusExercicios;
    }

    public void setStatusExercicios(String statusExercicios) {
        this.statusExercicios = statusExercicios;
    }
}
