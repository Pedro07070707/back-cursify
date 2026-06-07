package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "participantes_conversa")
public class ParticipanteConversa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "conversa_id")
    private Long conversaId;
    @Column(name = "usuario_id")
    private Long usuarioId;
    @Column(name = "entrou_em")
    private LocalDateTime entrouEm;
    @Column(name = "silenciado_ate")
    private LocalDateTime silenciadoAte;
    private Boolean arquivado;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getConversaId() { return conversaId; }
    public void setConversaId(Long conversaId) { this.conversaId = conversaId; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public LocalDateTime getEntrouEm() { return entrouEm; }
    public void setEntrouEm(LocalDateTime entrouEm) { this.entrouEm = entrouEm; }
    public LocalDateTime getSilenciadoAte() { return silenciadoAte; }
    public void setSilenciadoAte(LocalDateTime silenciadoAte) { this.silenciadoAte = silenciadoAte; }
    public Boolean getArquivado() { return arquivado; }
    public void setArquivado(Boolean arquivado) { this.arquivado = arquivado; }
}
