package com.itb.inf2cm.CursiFy.model.dto;

public class ResponderNoItemResponse {
    private Long questaoId;
    private Long alternativaId;
    private Boolean correta;
    private Integer tentativaNumero;
    private String feedback;

    public ResponderNoItemResponse(Long questaoId, Long alternativaId, Boolean correta, Integer tentativaNumero, String feedback) {
        this.questaoId = questaoId;
        this.alternativaId = alternativaId;
        this.correta = correta;
        this.tentativaNumero = tentativaNumero;
        this.feedback = feedback;
    }

    public Long getQuestaoId() { return questaoId; }
    public Long getAlternativaId() { return alternativaId; }
    public Boolean getCorreta() { return correta; }
    public Integer getTentativaNumero() { return tentativaNumero; }
    public String getFeedback() { return feedback; }
}
