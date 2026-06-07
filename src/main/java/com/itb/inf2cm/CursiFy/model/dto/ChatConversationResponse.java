package com.itb.inf2cm.CursiFy.model.dto;

public class ChatConversationResponse {
    private Long id;
    private String tipo;
    private Long trilhaId;
    private String nome;
    public ChatConversationResponse() {}
    public ChatConversationResponse(Long id, String tipo, Long trilhaId, String nome) {
        this.id = id; this.tipo = tipo; this.trilhaId = trilhaId; this.nome = nome;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Long getTrilhaId() { return trilhaId; }
    public void setTrilhaId(Long trilhaId) { this.trilhaId = trilhaId; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
