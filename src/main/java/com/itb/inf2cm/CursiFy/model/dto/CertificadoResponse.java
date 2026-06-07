package com.itb.inf2cm.CursiFy.model.dto;

import java.time.LocalDateTime;

public class CertificadoResponse {
    private Long cursoId;
    private Long usuarioId;
    private String codigoValidacao;
    private LocalDateTime emitidoEm;

    public CertificadoResponse(Long cursoId, Long usuarioId, String codigoValidacao, LocalDateTime emitidoEm) {
        this.cursoId = cursoId;
        this.usuarioId = usuarioId;
        this.codigoValidacao = codigoValidacao;
        this.emitidoEm = emitidoEm;
    }

    public Long getCursoId() { return cursoId; }
    public Long getUsuarioId() { return usuarioId; }
    public String getCodigoValidacao() { return codigoValidacao; }
    public LocalDateTime getEmitidoEm() { return emitidoEm; }
}
