package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.dto.AvaliarProjetoRequest;
import com.itb.inf2cm.CursiFy.model.dto.AvaliarProjetoResponse;
import com.itb.inf2cm.CursiFy.model.dto.ProjetoEnviadoRequest;
import com.itb.inf2cm.CursiFy.model.dto.ProjetoEnviadoResponse;
import com.itb.inf2cm.CursiFy.model.entity.NoTrilha;
import com.itb.inf2cm.CursiFy.model.entity.ProjetoEnviado;
import com.itb.inf2cm.CursiFy.model.entity.XpUsuario;
import com.itb.inf2cm.CursiFy.model.repository.NoTrilhaRepository;
import com.itb.inf2cm.CursiFy.model.repository.ProjetoEnviadoRepository;
import com.itb.inf2cm.CursiFy.model.repository.XpUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjetoEnviadoService {
    @Autowired private ProjetoEnviadoRepository projetoEnviadoRepository;
    @Autowired private NoTrilhaRepository noTrilhaRepository;
    @Autowired private XpUsuarioRepository xpUsuarioRepository;

    public ProjetoEnviadoResponse enviar(Long noId, ProjetoEnviadoRequest request) {
        NoTrilha no = noTrilhaRepository.findById(noId).orElseThrow(() -> new RuntimeException("No nao encontrado"));
        ProjetoEnviado projeto = new ProjetoEnviado();
        projeto.setNoId(noId);
        projeto.setUsuarioId(request.getUsuarioId());
        projeto.setRespostaTexto(request.getRespostaTexto());
        projeto.setArquivoUrl(request.getArquivoUrl());
        projeto.setStatus("PENDENTE");
        projeto.setEnviadoEm(LocalDateTime.now());
        projeto = projetoEnviadoRepository.save(projeto);
        return toResponse(projeto);
    }

    public ProjetoEnviadoResponse listarUltimo(Long noId, Long usuarioId) {
        return projetoEnviadoRepository.findTopByUsuarioIdAndNoIdOrderByEnviadoEmDesc(usuarioId, noId)
            .map(this::toResponse)
            .orElse(null);
    }

    public List<ProjetoEnviadoResponse> listarPendentes() {
        return projetoEnviadoRepository.findByStatusOrderByEnviadoEmDesc("PENDENTE")
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public AvaliarProjetoResponse avaliar(Long projetoId, AvaliarProjetoRequest request) {
        ProjetoEnviado projeto = projetoEnviadoRepository.findById(projetoId)
            .orElseThrow(() -> new RuntimeException("Projeto nao encontrado"));
        NoTrilha no = noTrilhaRepository.findById(projeto.getNoId())
            .orElseThrow(() -> new RuntimeException("No nao encontrado"));
        boolean aprovado = Boolean.TRUE.equals(request.getAprovado());
        projeto.setStatus(aprovado ? "APROVADO" : "REPROVADO");
        projeto.setFeedbackProfessor(request.getFeedbackProfessor());
        projeto.setAvaliadoPor(request.getAvaliadorId());
        projeto.setAvaliadoEm(LocalDateTime.now());
        projeto = projetoEnviadoRepository.save(projeto);

        int xpGanho = aprovado ? (no.getXpRecompensa() == null ? 10 : no.getXpRecompensa()) : 0;
        int xpTotal = 0;
        Long usuarioId = projeto.getUsuarioId();
        if (xpGanho > 0 && usuarioId != null) {
            XpUsuario xp = xpUsuarioRepository.findByUsuarioId(usuarioId).orElseGet(() -> {
                XpUsuario novo = new XpUsuario();
                novo.setUsuarioId(usuarioId);
                novo.setXpTotal(0);
                novo.setNivel(1);
                novo.setStreakAtual(0);
                novo.setStreakMaximo(0);
                return novo;
            });
            xp.setXpTotal((xp.getXpTotal() == null ? 0 : xp.getXpTotal()) + xpGanho);
            xp.setNivel(Math.max(1, (xp.getXpTotal() / 200) + 1));
            xp = xpUsuarioRepository.save(xp);
            xpTotal = xp.getXpTotal();
        }

        return new AvaliarProjetoResponse(projeto.getId(), projeto.getStatus(), xpGanho, xpTotal, projeto.getFeedbackProfessor());
    }

    private ProjetoEnviadoResponse toResponse(ProjetoEnviado projeto) {
        return new ProjetoEnviadoResponse(
            projeto.getId(),
            projeto.getNoId(),
            projeto.getUsuarioId(),
            projeto.getStatus(),
            projeto.getRespostaTexto(),
            projeto.getArquivoUrl(),
            projeto.getFeedbackProfessor(),
            projeto.getAvaliadoPor(),
            projeto.getEnviadoEm(),
            projeto.getAvaliadoEm()
        );
    }
}
