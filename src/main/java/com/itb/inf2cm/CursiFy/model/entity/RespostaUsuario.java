package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas_usuario")
public class RespostaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "questao_id", nullable = false)
    private Long questaoId;

    @Column(name = "alternativa_id")
    private Long alternativaId;

    @Column(name = "resposta_texto", length = 500)
    private String respostaTexto;

    @Column(nullable = false)
    private Boolean correta;

    @Column(name = "tentativa_numero", nullable = false)
    private Integer tentativaNumero;

    @Column(name = "respondido_em", nullable = false)
    private LocalDateTime respondidoEm;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getQuestaoId() { return questaoId; }
    public void setQuestaoId(Long questaoId) { this.questaoId = questaoId; }
    public Long getAlternativaId() { return alternativaId; }
    public void setAlternativaId(Long alternativaId) { this.alternativaId = alternativaId; }
    public String getRespostaTexto() { return respostaTexto; }
    public void setRespostaTexto(String respostaTexto) { this.respostaTexto = respostaTexto; }
    public Boolean getCorreta() { return correta; }
    public void setCorreta(Boolean correta) { this.correta = correta; }
    public Integer getTentativaNumero() { return tentativaNumero; }
    public void setTentativaNumero(Integer tentativaNumero) { this.tentativaNumero = tentativaNumero; }
    public LocalDateTime getRespondidoEm() { return respondidoEm; }
    public void setRespondidoEm(LocalDateTime respondidoEm) { this.respondidoEm = respondidoEm; }
}
