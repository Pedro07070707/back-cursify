package com.itb.inf2cm.CursiFy.model.dto;

public class RespostaItemRequest {
    private Long questaoId;
    private Long alternativaId;

    public Long getQuestaoId() { return questaoId; }
    public void setQuestaoId(Long questaoId) { this.questaoId = questaoId; }
    public Long getAlternativaId() { return alternativaId; }
    public void setAlternativaId(Long alternativaId) { this.alternativaId = alternativaId; }
}
