package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Matricula")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dataMatricula;

    @Column(length = 20, nullable = false)
    private String statusMatricula;

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

    public String getStatusMatricula() {
        return statusMatricula;
    }

    public void setStatusMatricula(String statusMatricula) {
        this.statusMatricula = statusMatricula;
    }
}
