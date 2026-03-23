package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

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

    @Column(length = 200, nullable = true)
    private String link;

    @Column(length = 20, nullable = false)
    private String statusExercicios;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatusExercicios() {
        return statusExercicios;
    }

    public void setStatusExercicios(String statusExercicios) {
        this.statusExercicios = statusExercicios;
    }
}
