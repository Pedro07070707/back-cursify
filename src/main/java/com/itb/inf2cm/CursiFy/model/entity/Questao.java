package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "questoes")
public class Questao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "no_id", nullable = false)
    private Long noId;

    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String enunciado;

    @Column(nullable = false, length = 30)
    private String tipo;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String explicacao;

    @Column(nullable = false)
    private Integer ordem;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getNoId() { return noId; }
    public void setNoId(Long noId) { this.noId = noId; }
    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getExplicacao() { return explicacao; }
    public void setExplicacao(String explicacao) { this.explicacao = explicacao; }
    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }
}
