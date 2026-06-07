package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.NoTrilha;
import com.itb.inf2cm.CursiFy.model.entity.ProgressoNo;
import com.itb.inf2cm.CursiFy.model.entity.Trilha;
import com.itb.inf2cm.CursiFy.model.repository.NoTrilhaRepository;
import com.itb.inf2cm.CursiFy.model.repository.ProgressoNoRepository;
import com.itb.inf2cm.CursiFy.model.repository.TrilhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class TrilhaService {

    @Autowired
    private TrilhaRepository trilhaRepository;

    @Autowired
    private NoTrilhaRepository noTrilhaRepository;

    @Autowired
    private ProgressoNoRepository progressoNoRepository;

    public List<Trilha> findAll() {
        return trilhaRepository.findAll().stream()
                .sorted(Comparator.comparing(Trilha::getId))
                .peek(trilha -> trilha.setNos(noTrilhaRepository.findByTrilha_IdOrderByOrdemAsc(trilha.getId())))
                .toList();
    }

    public List<Trilha> findAllForUser(Long usuarioId) {
        List<ProgressoNo> progresso = usuarioId == null ? List.of() : progressoNoRepository.findByUsuarioId(usuarioId);
        return trilhaRepository.findAll().stream()
                .sorted(Comparator.comparing(Trilha::getId))
                .peek(trilha -> trilha.setNos(noTrilhaRepository.findByTrilha_IdOrderByOrdemAsc(trilha.getId())))
                .peek(trilha -> applyUserProgress(trilha, progresso))
                .toList();
    }

    public Trilha findById(Long id) {
        Trilha trilha = trilhaRepository.findById(id).orElseThrow(() -> new RuntimeException("Trilha nao encontrada"));
        trilha.setNos(noTrilhaRepository.findByTrilha_IdOrderByOrdemAsc(id));
        return trilha;
    }

    public Trilha findByIdForUser(Long id, Long usuarioId) {
        Trilha trilha = findById(id);
        if (usuarioId != null) {
            applyUserProgress(trilha, progressoNoRepository.findByUsuarioId(usuarioId));
        }
        return trilha;
    }

    private void applyUserProgress(Trilha trilha, List<ProgressoNo> progresso) {
        if (trilha.getNos() == null) return;
        trilha.setNos(trilha.getNos().stream().map(no -> {
            ProgressoNo entry = progresso.stream().filter(p -> p.getNoId() != null && p.getNoId().equals(no.getId())).findFirst().orElse(null);
            if (entry == null) {
                return no;
            }
            String status = entry.getStatus() == null ? "LIBERADO" : entry.getStatus();
            if ("CONCLUIDO".equalsIgnoreCase(status)) {
                no.setAtivo(true);
            }
            return no;
        }).toList());
    }

    public Trilha save(Trilha trilha) {
        if (trilha.getCriadoEm() == null) {
            trilha.setCriadoEm(LocalDateTime.now());
        }
        if (trilha.getXpTotal() == null) {
            trilha.setXpTotal(0);
        }
        if (trilha.getAtivo() == null) {
            trilha.setAtivo(true);
        }
        return trilhaRepository.save(trilha);
    }

    public Trilha update(Long id, Trilha trilha) {
        Trilha existente = findById(id);
        existente.setTitulo(trilha.getTitulo());
        existente.setDescricao(trilha.getDescricao());
        existente.setSlug(trilha.getSlug());
        existente.setThumbnailUrl(trilha.getThumbnailUrl());
        existente.setMateria(trilha.getMateria());
        existente.setProfessorId(trilha.getProfessorId());
        existente.setXpTotal(trilha.getXpTotal());
        existente.setAtivo(trilha.getAtivo());
        return trilhaRepository.save(existente);
    }

    public void delete(Long id) {
        Trilha existente = findById(id);
        trilhaRepository.delete(existente);
    }

    public NoTrilha saveNo(Long trilhaId, NoTrilha noTrilha) {
        Trilha trilha = findById(trilhaId);
        noTrilha.setTrilha(trilha);
        if (noTrilha.getCriadoEm() == null) {
            noTrilha.setCriadoEm(LocalDateTime.now());
        }
        if (noTrilha.getXpRecompensa() == null) {
            noTrilha.setXpRecompensa(10);
        }
        if (noTrilha.getAtivo() == null) {
            noTrilha.setAtivo(true);
        }
        return noTrilhaRepository.save(noTrilha);
    }

    public NoTrilha updateNo(Long noId, NoTrilha noTrilha) {
        NoTrilha existente = noTrilhaRepository.findById(noId)
                .orElseThrow(() -> new RuntimeException("No da trilha nao encontrado"));
        existente.setTitulo(noTrilha.getTitulo());
        existente.setDescricao(noTrilha.getDescricao());
        existente.setTipo(noTrilha.getTipo());
        existente.setOrdem(noTrilha.getOrdem());
        existente.setXpRecompensa(noTrilha.getXpRecompensa());
        existente.setNotaMinima(noTrilha.getNotaMinima());
        existente.setMaxTentativas(noTrilha.getMaxTentativas());
        existente.setIcone(noTrilha.getIcone());
        existente.setAtivo(noTrilha.getAtivo());
        return noTrilhaRepository.save(existente);
    }

    public void deleteNo(Long noId) {
        NoTrilha existente = noTrilhaRepository.findById(noId)
                .orElseThrow(() -> new RuntimeException("No da trilha nao encontrado"));
        noTrilhaRepository.delete(existente);
    }
}
