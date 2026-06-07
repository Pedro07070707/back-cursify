package com.itb.inf2cm.CursiFy.model.dto;

public class AvaliarProjetoRequest {
    private Long avaliadorId;
    private Boolean aprovado;
    private String feedbackProfessor;

    public Long getAvaliadorId() { return avaliadorId; }
    public void setAvaliadorId(Long avaliadorId) { this.avaliadorId = avaliadorId; }
    public Boolean getAprovado() { return aprovado; }
    public void setAprovado(Boolean aprovado) { this.aprovado = aprovado; }
    public String getFeedbackProfessor() { return feedbackProfessor; }
    public void setFeedbackProfessor(String feedbackProfessor) { this.feedbackProfessor = feedbackProfessor; }
}
