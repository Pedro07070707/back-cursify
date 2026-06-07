package com.itb.inf2cm.CursiFy.model.dto;

public class ConcluirNoResponse {
    private Long noId;
    private Long usuarioId;
    private String status;
    private Integer xpGanho;
    private Integer xpTotal;

    public ConcluirNoResponse(Long noId, Long usuarioId, String status, Integer xpGanho, Integer xpTotal) {
        this.noId = noId;
        this.usuarioId = usuarioId;
        this.status = status;
        this.xpGanho = xpGanho;
        this.xpTotal = xpTotal;
    }

    public Long getNoId() { return noId; }
    public Long getUsuarioId() { return usuarioId; }
    public String getStatus() { return status; }
    public Integer getXpGanho() { return xpGanho; }
    public Integer getXpTotal() { return xpTotal; }
}
