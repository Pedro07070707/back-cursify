package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "projetos_enviados")
public class ProjetoEnviado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "no_id", nullable = false)
    private Long noId;

    @Column(name = "resposta_texto", columnDefinition = "NVARCHAR(MAX)")
    private String respostaTexto;

    @Column(name = "arquivo_url", length = 500)
    private String arquivoUrl;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "feedback_professor", columnDefinition = "NVARCHAR(MAX)")
    private String feedbackProfessor;

    @Column(name = "avaliado_por")
    private Long avaliadoPor;

    @Column(name = "enviado_em")
    private LocalDateTime enviadoEm;

    @Column(name = "avaliado_em")
    private LocalDateTime avaliadoEm;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getNoId() { return noId; }
    public void setNoId(Long noId) { this.noId = noId; }
    public String getRespostaTexto() { return respostaTexto; }
    public void setRespostaTexto(String respostaTexto) { this.respostaTexto = respostaTexto; }
    public String getArquivoUrl() { return arquivoUrl; }
    public void setArquivoUrl(String arquivoUrl) { this.arquivoUrl = arquivoUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFeedbackProfessor() { return feedbackProfessor; }
    public void setFeedbackProfessor(String feedbackProfessor) { this.feedbackProfessor = feedbackProfessor; }
    public Long getAvaliadoPor() { return avaliadoPor; }
    public void setAvaliadoPor(Long avaliadoPor) { this.avaliadoPor = avaliadoPor; }
    public LocalDateTime getEnviadoEm() { return enviadoEm; }
    public void setEnviadoEm(LocalDateTime enviadoEm) { this.enviadoEm = enviadoEm; }
    public LocalDateTime getAvaliadoEm() { return avaliadoEm; }
    public void setAvaliadoEm(LocalDateTime avaliadoEm) { this.avaliadoEm = avaliadoEm; }
}
