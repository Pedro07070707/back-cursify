package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataChat;

    private Boolean statusChat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataChat() {
        return dataChat;
    }

    public void setDataChat(LocalDateTime dataChat) {
        this.dataChat = dataChat;
    }

    public Boolean getStatusChat() {
        return statusChat;
    }

    public void setStatusChat(Boolean statusChat) {
        this.statusChat = statusChat;
    }
}
