package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "progresso_nos")
public class ProgressoNo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "no_id")
    private Long noId;

    private String status;

    @Column(name = "nota_obtida")
    private Double notaObtida;

    private Integer tentativas;

    @Column(name = "xp_ganho")
    private Integer xpGanho;

    @Column(name = "concluido_em")
    private LocalDateTime concluidoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getNoId() { return noId; }
    public void setNoId(Long noId) { this.noId = noId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getNotaObtida() { return notaObtida; }
    public void setNotaObtida(Double notaObtida) { this.notaObtida = notaObtida; }
    public Integer getTentativas() { return tentativas; }
    public void setTentativas(Integer tentativas) { this.tentativas = tentativas; }
    public Integer getXpGanho() { return xpGanho; }
    public void setXpGanho(Integer xpGanho) { this.xpGanho = xpGanho; }
    public LocalDateTime getConcluidoEm() { return concluidoEm; }
    public void setConcluidoEm(LocalDateTime concluidoEm) { this.concluidoEm = concluidoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}
