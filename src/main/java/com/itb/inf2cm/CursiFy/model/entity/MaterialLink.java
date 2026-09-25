package com.itb.inf2cm.CursiFy.model.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class MaterialLink {
    private String titulo;
    private String url;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
