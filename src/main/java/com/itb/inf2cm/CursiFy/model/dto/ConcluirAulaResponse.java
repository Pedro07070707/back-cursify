package com.itb.inf2cm.CursiFy.model.dto;

public class ConcluirAulaResponse {
    private Long aulaId;
    private Long usuarioId;
    private String status;
    private Boolean certificadoLiberado;
    private CertificadoResponse certificado;

    public ConcluirAulaResponse(Long aulaId, Long usuarioId, String status) {
        this.aulaId = aulaId;
        this.usuarioId = usuarioId;
        this.status = status;
    }

    public Long getAulaId() { return aulaId; }
    public Long getUsuarioId() { return usuarioId; }
    public String getStatus() { return status; }
    public Boolean getCertificadoLiberado() { return certificadoLiberado; }
    public void setCertificadoLiberado(Boolean certificadoLiberado) { this.certificadoLiberado = certificadoLiberado; }
    public CertificadoResponse getCertificado() { return certificado; }
    public void setCertificado(CertificadoResponse certificado) { this.certificado = certificado; }
}
