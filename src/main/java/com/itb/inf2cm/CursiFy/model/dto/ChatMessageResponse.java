package com.itb.inf2cm.CursiFy.model.dto;

import java.time.LocalDateTime;

public class ChatMessageResponse {
    private Long id;
    private Long conversaId;
    private Long remetenteId;
    private String conteudo;
    private String tipo;
    private LocalDateTime enviadoEm;
    public ChatMessageResponse() {}
    public ChatMessageResponse(Long id, Long conversaId, Long remetenteId, String conteudo, String tipo, LocalDateTime enviadoEm) {
        this.id = id; this.conversaId = conversaId; this.remetenteId = remetenteId; this.conteudo = conteudo; this.tipo = tipo; this.enviadoEm = enviadoEm;
    }
    public Long getId() { return id; }
    public Long getConversaId() { return conversaId; }
    public Long getRemetenteId() { return remetenteId; }
    public String getConteudo() { return conteudo; }
    public String getTipo() { return tipo; }
    public LocalDateTime getEnviadoEm() { return enviadoEm; }
}
