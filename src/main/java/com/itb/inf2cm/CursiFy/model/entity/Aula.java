package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "aulas")
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "modulo_id", nullable = false)
    private Long moduloId;

    @Column(length = 150, nullable = false)
    private String titulo;

    @Column(length = 20, nullable = false)
    private String tipo;

    @Column(name = "conteudo_url", length = 500)
    private String conteudoUrl;

    @Column(name = "conteudo_texto", columnDefinition = "NVARCHAR(MAX)")
    private String conteudoTexto;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    @Column(nullable = false)
    private Integer ordem;

    private Boolean gratuita;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getModuloId() { return moduloId; }
    public void setModuloId(Long moduloId) { this.moduloId = moduloId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getConteudoUrl() { return conteudoUrl; }
    public void setConteudoUrl(String conteudoUrl) { this.conteudoUrl = conteudoUrl; }
    public String getConteudoTexto() { return conteudoTexto; }
    public void setConteudoTexto(String conteudoTexto) { this.conteudoTexto = conteudoTexto; }
    public Integer getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(Integer duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }
    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }
    public Boolean getGratuita() { return gratuita; }
    public void setGratuita(Boolean gratuita) { this.gratuita = gratuita; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}
