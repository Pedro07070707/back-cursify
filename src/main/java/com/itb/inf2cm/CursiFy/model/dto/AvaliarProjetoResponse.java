package com.itb.inf2cm.CursiFy.model.dto;

public class AvaliarProjetoResponse {
    private Long projetoId;
    private String status;
    private Integer xpGanho;
    private Integer xpTotal;
    private String feedbackProfessor;

    public AvaliarProjetoResponse(Long projetoId, String status, Integer xpGanho, Integer xpTotal, String feedbackProfessor) {
        this.projetoId = projetoId;
        this.status = status;
        this.xpGanho = xpGanho;
        this.xpTotal = xpTotal;
        this.feedbackProfessor = feedbackProfessor;
    }

    public Long getProjetoId() { return projetoId; }
    public String getStatus() { return status; }
    public Integer getXpGanho() { return xpGanho; }
    public Integer getXpTotal() { return xpTotal; }
    public String getFeedbackProfessor() { return feedbackProfessor; }
}
