package com.itb.inf2cm.CursiFy.model.dto;

public class PublicTrackNodeResponse {

    private Long id;
    private String titulo;
    private String tipo;
    private String estado;
    private int xp;
    private String icone;
    private boolean destaque;
    private boolean checkpoint;

    public PublicTrackNodeResponse() {
    }

    public PublicTrackNodeResponse(Long id, String titulo, String tipo, String estado, int xp, String icone, boolean destaque, boolean checkpoint) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.estado = estado;
        this.xp = xp;
        this.icone = icone;
        this.destaque = destaque;
        this.checkpoint = checkpoint;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isDestaque() {
        return destaque;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    public boolean isCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(boolean checkpoint) {
        this.checkpoint = checkpoint;
    }
}
