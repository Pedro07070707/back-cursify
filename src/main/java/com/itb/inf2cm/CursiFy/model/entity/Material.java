package com.itb.inf2cm.CursiFy.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Material")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String titulo;

    @Column(length = 100, nullable = false)
    private String subtitulo;

    @Column(length = 500, nullable = false)
    private String conteudo;

    @ElementCollection
    @CollectionTable(name = "Material_links", joinColumns = @JoinColumn(name = "material_id"))
    @OrderColumn(name = "ordem")
    @AttributeOverrides({
            @AttributeOverride(name = "titulo", column = @Column(name = "titulo", length = 150, nullable = false)),
            @AttributeOverride(name = "url", column = @Column(name = "url", length = 500, nullable = false))
    })
    private List<MaterialLink> links = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"senha", "cpf", "email", "dataCadastro", "statusUsuario", "nivelAcesso"})
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Column(length = 20, nullable = false)
    private String statusMaterial = "Nao concluido";

    private static final String STATUS_PADRAO = "Nao concluido";

    @PrePersist
    @PreUpdate
    private void garantirStatus() {
        if (statusMaterial == null || statusMaterial.isBlank()) {
            statusMaterial = STATUS_PADRAO;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public List<MaterialLink> getLinks() { return links; }
    public void setLinks(List<MaterialLink> links) { this.links = links == null ? new ArrayList<>() : links; }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getStatusMaterial() {
        return statusMaterial;
    }

    public void setStatusMaterial(String statusMaterial) {
        this.statusMaterial = statusMaterial == null || statusMaterial.isBlank()
                ? STATUS_PADRAO
                : statusMaterial;
    }
}
