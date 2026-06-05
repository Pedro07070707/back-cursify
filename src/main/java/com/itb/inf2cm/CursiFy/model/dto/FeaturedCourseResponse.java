package com.itb.inf2cm.CursiFy.model.dto;

public class FeaturedCourseResponse {

    private Long id;
    private String nome;
    private String descricao;
    private String categoria;
    private int cargaHoraria;
    private String statusCurso;

    public FeaturedCourseResponse() {
    }

    public FeaturedCourseResponse(Long id, String nome, String descricao, String categoria, int cargaHoraria, String statusCurso) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.cargaHoraria = cargaHoraria;
        this.statusCurso = statusCurso;
    }

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getStatusCurso() {
        return statusCurso;
    }

    public void setStatusCurso(String statusCurso) {
        this.statusCurso = statusCurso;
    }
}
