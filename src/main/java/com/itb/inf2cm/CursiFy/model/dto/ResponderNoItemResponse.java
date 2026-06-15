package com.itb.inf2cm.CursiFy.model.dto;

public class ResponderNoItemResponse {
    private Long questaoId;
    private String enunciado;
    private Long alternativaId;
    private Long alternativaCorretaId;
    private Boolean correta;
    private Integer tentativaNumero;
    private String feedback;

    public ResponderNoItemResponse(Long questaoId, String enunciado, Long alternativaId, Long alternativaCorretaId, Boolean correta, Integer tentativaNumero, String feedback) {
        this.questaoId = questaoId;
        this.enunciado = enunciado;
        this.alternativaId = alternativaId;
        this.alternativaCorretaId = alternativaCorretaId;
        this.correta = correta;
        this.tentativaNumero = tentativaNumero;
        this.feedback = feedback;
    }

    public Long getQuestaoId() { return questaoId; }
    public String getEnunciado() { return enunciado; }
    public Long getAlternativaId() { return alternativaId; }
    public Long getAlternativaCorretaId() { return alternativaCorretaId; }
    public Boolean getCorreta() { return correta; }
    public Integer getTentativaNumero() { return tentativaNumero; }
    public String getFeedback() { return feedback; }
}
