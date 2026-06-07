package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "modulos")
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "curso_id", nullable = false)
    private Long cursoId;

    @Column(length = 150, nullable = false)
    private String titulo;

    @Column(nullable = false)
    private Integer ordem;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id")
    private List<Aula> aulas;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
    public List<Aula> getAulas() { return aulas; }
    public void setAulas(List<Aula> aulas) { this.aulas = aulas; }
}
