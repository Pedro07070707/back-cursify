package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "UsuarioChat")
public class UsuarioChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
