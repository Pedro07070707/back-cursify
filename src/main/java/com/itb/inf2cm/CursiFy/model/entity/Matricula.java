package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Matricula")
public class Matricula extends Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dataMatricula;

    private Boolean statusMatricula;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(String dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Boolean getStatusMatricula() {
        return statusMatricula;
    }

    public void setStatusMatricula(Boolean statusMatricula) {
        this.statusMatricula = statusMatricula;
    }
}
