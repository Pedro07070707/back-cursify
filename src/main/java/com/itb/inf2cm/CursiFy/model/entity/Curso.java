package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;

@Entity
@Table(name = "Curso")
public class Curso {

    @Id
    @GeneratedValue(strategy)
    private Long id;
    private String titulo;
    private String descricao;
    private int cargaHoraria;
    private Double preco;
    private String dataCriacao;
}
