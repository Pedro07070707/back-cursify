package com.itb.inf2cm.CursiFy.model.dto;

import java.util.List;

public class NoQuestaoResponse {
    private Long id;
    private String enunciado;
    private String tipo;
    private String explicacao;
    private Integer ordem;
    private List<NoAlternativaResponse> alternativas;

    public NoQuestaoResponse(Long id, String enunciado, String tipo, String explicacao, Integer ordem, List<NoAlternativaResponse> alternativas) {
        this.id = id;
        this.enunciado = enunciado;
        this.tipo = tipo;
        this.explicacao = explicacao;
        this.ordem = ordem;
        this.alternativas = alternativas;
    }

    public Long getId() { return id; }
    public String getEnunciado() { return enunciado; }
    public String getTipo() { return tipo; }
    public String getExplicacao() { return explicacao; }
    public Integer getOrdem() { return ordem; }
    public List<NoAlternativaResponse> getAlternativas() { return alternativas; }
}
