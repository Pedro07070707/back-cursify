package com.itb.inf2cm.CursiFy.model.dto;

public class CursoAulaResponse {
    private Long id;
    private String titulo;
    private String subtitulo;
    private String conteudo;
    private String link;
    private String status;

    public CursoAulaResponse(Long id, String titulo, String subtitulo, String conteudo, String link, String status) {
        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.conteudo = conteudo;
        this.link = link;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getSubtitulo() { return subtitulo; }
    public String getConteudo() { return conteudo; }
    public String getLink() { return link; }
    public String getStatus() { return status; }
}
