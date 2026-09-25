package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PreferenciaCurso", uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "curso_id", "tipo"}))
public class PreferenciaCurso {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "usuario_id", nullable = false) private Long usuarioId;
    @Column(name = "curso_id", nullable = false) private Long cursoId;
    @Column(nullable = false, length = 30) private String tipo;
    @Column(length = 2000) private String valor;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Long getUsuarioId(){return usuarioId;} public void setUsuarioId(Long v){usuarioId=v;}
    public Long getCursoId(){return cursoId;} public void setCursoId(Long v){cursoId=v;}
    public String getTipo(){return tipo;} public void setTipo(String v){tipo=v;}
    public String getValor(){return valor;} public void setValor(String v){valor=v;}
}
