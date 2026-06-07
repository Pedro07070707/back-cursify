package com.itb.inf2cm.CursiFy.model.dto;

public class MatriculaResponse {
    private Long cursoId;
    private Long usuarioId;
    private String status;

    public MatriculaResponse(Long cursoId, Long usuarioId, String status) {
        this.cursoId = cursoId;
        this.usuarioId = usuarioId;
        this.status = status;
    }

    public Long getCursoId() { return cursoId; }
    public Long getUsuarioId() { return usuarioId; }
    public String getStatus() { return status; }
}
