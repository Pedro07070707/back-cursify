package com.itb.inf2cm.CursiFy.model.dto;

import java.util.List;

public class PublicTrackResponse {

    private Long id;
    private String titulo;
    private String materia;
    private String professor;
    private int xpTotal;
    private String dificuldade;
    private String thumbnail;
    private int progresso;
    private int streak;
    private String proximoNo;
    private List<PublicTrackNodeResponse> nos;

    public PublicTrackResponse() {
    }

    public PublicTrackResponse(Long id, String titulo, String materia, String professor, int xpTotal, String dificuldade, String thumbnail, int progresso, int streak, String proximoNo, List<PublicTrackNodeResponse> nos) {
        this.id = id;
        this.titulo = titulo;
        this.materia = materia;
        this.professor = professor;
        this.xpTotal = xpTotal;
        this.dificuldade = dificuldade;
        this.thumbnail = thumbnail;
        this.progresso = progresso;
        this.streak = streak;
        this.proximoNo = proximoNo;
        this.nos = nos;
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

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getXpTotal() {
        return xpTotal;
    }

    public void setXpTotal(int xpTotal) {
        this.xpTotal = xpTotal;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getProgresso() {
        return progresso;
    }

    public void setProgresso(int progresso) {
        this.progresso = progresso;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public String getProximoNo() {
        return proximoNo;
    }

    public void setProximoNo(String proximoNo) {
        this.proximoNo = proximoNo;
    }

    public List<PublicTrackNodeResponse> getNos() {
        return nos;
    }

    public void setNos(List<PublicTrackNodeResponse> nos) {
        this.nos = nos;
    }
}
