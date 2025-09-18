package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String titulo;

    @Column(length = 50)
    private String descricao;

    @Column(length = 50)
    private int cargaHoraria;

    @Column(length = 50)
    private Double preco;

    @Column(length = 50)
    private String dataCriacao;
}
