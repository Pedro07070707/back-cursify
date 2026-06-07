package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "xp_usuario")
public class XpUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "xp_total")
    private Integer xpTotal;

    private Integer nivel;

    @Column(name = "streak_atual")
    private Integer streakAtual;

    @Column(name = "streak_maximo")
    private Integer streakMaximo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Integer getXpTotal() { return xpTotal; }
    public void setXpTotal(Integer xpTotal) { this.xpTotal = xpTotal; }
    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }
    public Integer getStreakAtual() { return streakAtual; }
    public void setStreakAtual(Integer streakAtual) { this.streakAtual = streakAtual; }
    public Integer getStreakMaximo() { return streakMaximo; }
    public void setStreakMaximo(Integer streakMaximo) { this.streakMaximo = streakMaximo; }
}
