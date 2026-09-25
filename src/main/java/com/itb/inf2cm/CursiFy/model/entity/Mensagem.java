package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Mensagem")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "remetente_id", nullable = false)
    private Long remetenteId;

    @Column(name = "destinatario_id")
    private Long destinatarioId;


    @Column(length = 500, nullable = false)
    private String conteudo;

    @Column(nullable = false)
    private LocalDateTime dataMensagem;

    @Column(length = 20, nullable = false)
    private String statusMensagem;

    @PrePersist
    private void defaults() {
        if (dataMensagem == null) dataMensagem = com.itb.inf2cm.CursiFy.config.ClockConfig.now();
        if (statusMensagem == null || statusMensagem.isBlank()) statusMensagem = "Enviado";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRemetenteId() { return remetenteId; }
    public void setRemetenteId(Long remetenteId) { this.remetenteId = remetenteId; }
    public Long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataMensagem() {
        return dataMensagem;
    }

    public void setDataMensagem(LocalDateTime dataMensagem) {
        this.dataMensagem = dataMensagem;
    }

    public String getStatusMensagem() {
        return statusMensagem;
    }

    public void setStatusMensagem(String statusMensagem) {
        this.statusMensagem = statusMensagem;
    }
}
