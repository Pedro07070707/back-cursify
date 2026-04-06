package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    
    @Column(length = 500)
    private String link1;

    @Column(length = 200)
    private String nomeLink1;

    @Column(length = 500)
    private String link2;

    @Column(length = 200)
    private String nomeLink2;

    @Column(length = 500)
    private String link3;

    @Column(length = 200)
    private String nomeLink3;

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
    
    public String getLink1() {
        return link1;
    }

    public void setLink1(String link1) {
        this.link1 = link1;
    }

    public String getNomeLink1() {
        return nomeLink1;
    }

    public void setNomeLink1(String nomeLink1) {
        this.nomeLink1 = nomeLink1;
    }

    public String getLink2() {
        return link2;
    }

    public void setLink2(String link2) {
        this.link2 = link2;
    }

    public String getNomeLink2() {
        return nomeLink2;
    }

    public void setNomeLink2(String nomeLink2) {
        this.nomeLink2 = nomeLink2;
    }

    public String getLink3() {
        return link3;
    }

    public void setLink3(String link3) {
        this.link3 = link3;
    }

    public String getNomeLink3() {
        return nomeLink3;
    }

    public void setNomeLink3(String nomeLink3) {
        this.nomeLink3 = nomeLink3;
    }
}
