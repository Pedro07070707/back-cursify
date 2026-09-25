package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "mensagem_id", nullable = false)
    private Mensagem mensagem;

    @Column(length = 50, nullable = false)
    private String remetente;

    @Column(name = "remetente_id", nullable = false)
    private Long remetenteId;

    /* Compatibilidade com o esquema legado do Somee. */
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "destinatario_id", nullable = false)
    private Long destinatarioId;

    @Column(name = "curso_id")
    private Long cursoId;

    @Transient
    private String cursoNome;

    @Column(nullable = false)
    private LocalDateTime dataChat;

    @Column(length = 20, nullable = false)
    private String statusChat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Mensagem getMensagem() { return mensagem; }
    public void setMensagem(Mensagem mensagem) { this.mensagem = mensagem; }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public Long getRemetenteId() { return remetenteId; }
    public void setRemetenteId(Long remetenteId) { this.remetenteId = remetenteId; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }
    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }
    public String getCursoNome() { return cursoNome; }
    public void setCursoNome(String cursoNome) { this.cursoNome = cursoNome; }

    public LocalDateTime getDataChat() {
        return dataChat;
    }

    public void setDataChat(LocalDateTime dataChat) {
        this.dataChat = dataChat;
    }

    public String getStatusChat() {
        return statusChat;
    }

    public void setStatusChat(String statusChat) {
        this.statusChat = statusChat;
    }
}
