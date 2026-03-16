package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Atividades")
public class Atividades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroAtividade;

    @Column(length = 500, nullable = false)
    private String enunciadoAtividade;

    @Column(length = 100, nullable = false)
    private String alternativaAtividade;

    @Column(length = 3, nullable = false)
    private int statusAtividade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroAtividade() {
        return numeroAtividade;
    }

    public void setNumeroAtividade(Long numeroAtividade) {
        this.numeroAtividade = numeroAtividade;
    }

    public String getEnunciadoAtividade() {
        return enunciadoAtividade;
    }

    public void setEnunciadoAtividade(String enunciadoAtividade) {
        this.enunciadoAtividade = enunciadoAtividade;
    }

    public String getAlternativaAtividade() {
        return alternativaAtividade;
    }

    public void setAlternativaAtividade(String alternativaAtividade) {
        this.alternativaAtividade = alternativaAtividade;
    }

    public int getStatusAtividade() {
        return statusAtividade;
    }

    public void setStatusAtividade(int statusAtividade) {
        this.statusAtividade = statusAtividade;
    }
}
