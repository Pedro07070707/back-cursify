package com.itb.inf2cm.CursiFy.model.dto;

public class ConcluirAulaRequest {
    private Long usuarioId;
    private Long cursoId;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }
}
