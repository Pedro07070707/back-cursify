package com.itb.inf2cm.CursiFy.model.dto;

import java.util.List;

public class CursoModuloResponse {
    private Long id;
    private String titulo;
    private List<CursoAulaResponse> aulas;

    public CursoModuloResponse(Long id, String titulo, List<CursoAulaResponse> aulas) {
        this.id = id;
        this.titulo = titulo;
        this.aulas = aulas;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public List<CursoAulaResponse> getAulas() { return aulas; }
}
