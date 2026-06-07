package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensagens")
public class MensagemChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "conversa_id")
    private Long conversaId;
    @Column(name = "remetente_id")
    private Long remetenteId;
    private String conteudo;
    private String tipo;
    @Column(name = "anexo_url")
    private String anexoUrl;
    @Column(name = "enviado_em")
    private LocalDateTime enviadoEm;
    @Column(name = "editado_em")
    private LocalDateTime editadoEm;
    private Boolean deletado;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getConversaId() { return conversaId; }
    public void setConversaId(Long conversaId) { this.conversaId = conversaId; }
    public Long getRemetenteId() { return remetenteId; }
    public void setRemetenteId(Long remetenteId) { this.remetenteId = remetenteId; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getAnexoUrl() { return anexoUrl; }
    public void setAnexoUrl(String anexoUrl) { this.anexoUrl = anexoUrl; }
    public LocalDateTime getEnviadoEm() { return enviadoEm; }
    public void setEnviadoEm(LocalDateTime enviadoEm) { this.enviadoEm = enviadoEm; }
    public LocalDateTime getEditadoEm() { return editadoEm; }
    public void setEditadoEm(LocalDateTime editadoEm) { this.editadoEm = editadoEm; }
    public Boolean getDeletado() { return deletado; }
    public void setDeletado(Boolean deletado) { this.deletado = deletado; }
}
