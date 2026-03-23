package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String remetente;

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

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

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
