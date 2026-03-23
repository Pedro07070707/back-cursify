package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Curso")
public class Curso{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 100, nullable = false)
    private String descricao;

    @Column(length = 100, nullable = false)
    private String categoria;

    @Column(length = 50, nullable = false)
    private int cargaHoraria;

    //@Column(length = 50, nullable = false)
    //private Double preco;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(length = 20, nullable = false)
    private String statusCurso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatusCurso() {
        return statusCurso;
    }

    public void setStatusCurso(String statusCurso) {
        this.statusCurso = statusCurso;
    }
}
