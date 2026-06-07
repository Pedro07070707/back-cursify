package com.itb.inf2cm.CursiFy.model.dto;

import java.util.List;

public class CursoDetalheResponse {
    private Long id;
    private String nome;
    private String descricao;
    private String categoria;
    private int cargaHoraria;
    private String statusCurso;
    private boolean matriculado;
    private List<CursoModuloResponse> modulos;

    public CursoDetalheResponse(Long id, String nome, String descricao, String categoria, int cargaHoraria, String statusCurso, boolean matriculado, List<CursoModuloResponse> modulos) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.cargaHoraria = cargaHoraria;
        this.statusCurso = statusCurso;
        this.matriculado = matriculado;
        this.modulos = modulos;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getCategoria() { return categoria; }
    public int getCargaHoraria() { return cargaHoraria; }
    public String getStatusCurso() { return statusCurso; }
    public boolean isMatriculado() { return matriculado; }
    public List<CursoModuloResponse> getModulos() { return modulos; }
}
