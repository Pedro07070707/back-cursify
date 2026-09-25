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

    @Column(length = 2000, nullable = false)
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

    @Column(name = "curso_aprovado", length = 20, nullable = false)
    private String cursoAprovado = "Pendente";

    @Column(name = "numero_alunos", nullable = false)
    private Integer numeroAlunos = 0;

    @Column(name = "motivo_recusa", length = 2000)
    private String motivoRecusa;

    @Transient
    private Long professorId;

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

    public Integer getNumeroAlunos() {
        return numeroAlunos;
    }

    public void setNumeroAlunos(Integer numeroAlunos) {
        this.numeroAlunos = numeroAlunos == null ? 0 : numeroAlunos;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getCursoAprovado() {
        return cursoAprovado;
    }

    public void setCursoAprovado(String cursoAprovado) {
        this.cursoAprovado = cursoAprovado == null || cursoAprovado.isBlank() ? "Pendente" : cursoAprovado;
    }

    public String getMotivoRecusa() { return motivoRecusa; }
    public void setMotivoRecusa(String motivoRecusa) { this.motivoRecusa = motivoRecusa; }
}
