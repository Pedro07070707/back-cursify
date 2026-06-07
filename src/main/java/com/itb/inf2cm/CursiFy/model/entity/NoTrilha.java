package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "nos_trilha")
public class NoTrilha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trilha_id")
    private Trilha trilha;

    @Column(length = 150, nullable = false)
    private String titulo;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String descricao;

    @Column(length = 20, nullable = false)
    private String tipo;

    private Integer ordem;

    @Column(name = "xp_recompensa")
    private Integer xpRecompensa;

    @Column(name = "nota_minima")
    private Double notaMinima;

    @Column(name = "max_tentativas")
    private Integer maxTentativas;

    @Column(length = 50)
    private String icone;

    private Boolean ativo;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Trilha getTrilha() { return trilha; }
    public void setTrilha(Trilha trilha) { this.trilha = trilha; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }
    public Integer getXpRecompensa() { return xpRecompensa; }
    public void setXpRecompensa(Integer xpRecompensa) { this.xpRecompensa = xpRecompensa; }
    public Double getNotaMinima() { return notaMinima; }
    public void setNotaMinima(Double notaMinima) { this.notaMinima = notaMinima; }
    public Integer getMaxTentativas() { return maxTentativas; }
    public void setMaxTentativas(Integer maxTentativas) { this.maxTentativas = maxTentativas; }
    public String getIcone() { return icone; }
    public void setIcone(String icone) { this.icone = icone; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}
