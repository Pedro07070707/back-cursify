package com.itb.inf2cm.CursiFy.model.dto;

import java.util.List;

public class ResponderNoRequest {
    private Long usuarioId;
    private List<RespostaItemRequest> respostas;
    private String respostaTexto;
    private String arquivoUrl;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public List<RespostaItemRequest> getRespostas() { return respostas; }
    public void setRespostas(List<RespostaItemRequest> respostas) { this.respostas = respostas; }
    public String getRespostaTexto() { return respostaTexto; }
    public void setRespostaTexto(String respostaTexto) { this.respostaTexto = respostaTexto; }
    public String getArquivoUrl() { return arquivoUrl; }
    public void setArquivoUrl(String arquivoUrl) { this.arquivoUrl = arquivoUrl; }
}
