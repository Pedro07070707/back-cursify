package com.itb.inf2cm.CursiFy.model.dto;

public class ResponderNoResponse {
    private Long noId;
    private Long usuarioId;
    private String tipoNo;
    private Integer acertos;
    private Integer total;
    private Double nota;
    private Double notaMinima;
    private Integer xpGanho;
    private Integer xpTotal;
    private Boolean aprovado;
    private Integer tentativas;
    private String mensagem;
    private java.util.List<ResponderNoItemResponse> detalhes;

    public ResponderNoResponse(
        Long noId,
        Long usuarioId,
        String tipoNo,
        Integer acertos,
        Integer total,
        Double nota,
        Double notaMinima,
        Integer xpGanho,
        Integer xpTotal,
        Boolean aprovado,
        Integer tentativas,
        String mensagem,
        java.util.List<ResponderNoItemResponse> detalhes
    ) {
        this.noId = noId;
        this.usuarioId = usuarioId;
        this.tipoNo = tipoNo;
        this.acertos = acertos;
        this.total = total;
        this.nota = nota;
        this.notaMinima = notaMinima;
        this.xpGanho = xpGanho;
        this.xpTotal = xpTotal;
        this.aprovado = aprovado;
        this.tentativas = tentativas;
        this.mensagem = mensagem;
        this.detalhes = detalhes;
    }

    public Long getNoId() { return noId; }
    public Long getUsuarioId() { return usuarioId; }
    public String getTipoNo() { return tipoNo; }
    public Integer getAcertos() { return acertos; }
    public Integer getTotal() { return total; }
    public Double getNota() { return nota; }
    public Double getNotaMinima() { return notaMinima; }
    public Integer getXpGanho() { return xpGanho; }
    public Integer getXpTotal() { return xpTotal; }
    public Boolean getAprovado() { return aprovado; }
    public Integer getTentativas() { return tentativas; }
    public String getMensagem() { return mensagem; }
    public java.util.List<ResponderNoItemResponse> getDetalhes() { return detalhes; }
}
