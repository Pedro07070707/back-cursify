package com.itb.inf2cm.CursiFy.model.dto;

public class NoAlternativaResponse {
    private Long id;
    private String texto;
    private Integer ordem;

    public NoAlternativaResponse(Long id, String texto, Integer ordem) {
        this.id = id;
        this.texto = texto;
        this.ordem = ordem;
    }

    public Long getId() { return id; }
    public String getTexto() { return texto; }
    public Integer getOrdem() { return ordem; }
}
