package com.itb.inf2cm.CursiFy.model.dto;

import java.time.LocalDateTime;

public class ProjetoEnviadoResponse {
    private Long id;
    private Long noId;
    private Long usuarioId;
    private String status;
    private String respostaTexto;
    private String arquivoUrl;
    private String feedbackProfessor;
    private Long avaliadoPor;
    private LocalDateTime enviadoEm;
    private LocalDateTime avaliadoEm;

    public ProjetoEnviadoResponse(Long id, Long noId, Long usuarioId, String status, String respostaTexto, String arquivoUrl, String feedbackProfessor, Long avaliadoPor, LocalDateTime enviadoEm, LocalDateTime avaliadoEm) {
        this.id = id;
        this.noId = noId;
        this.usuarioId = usuarioId;
        this.status = status;
        this.respostaTexto = respostaTexto;
        this.arquivoUrl = arquivoUrl;
        this.feedbackProfessor = feedbackProfessor;
        this.avaliadoPor = avaliadoPor;
        this.enviadoEm = enviadoEm;
        this.avaliadoEm = avaliadoEm;
    }

    public Long getId() { return id; }
    public Long getNoId() { return noId; }
    public Long getUsuarioId() { return usuarioId; }
    public String getStatus() { return status; }
    public String getRespostaTexto() { return respostaTexto; }
    public String getArquivoUrl() { return arquivoUrl; }
    public String getFeedbackProfessor() { return feedbackProfessor; }
    public Long getAvaliadoPor() { return avaliadoPor; }
    public LocalDateTime getEnviadoEm() { return enviadoEm; }
    public LocalDateTime getAvaliadoEm() { return avaliadoEm; }
}
