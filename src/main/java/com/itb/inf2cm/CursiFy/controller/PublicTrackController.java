package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.dto.PublicTrackNodeResponse;
import com.itb.inf2cm.CursiFy.model.dto.PublicTrackResponse;
import com.itb.inf2cm.CursiFy.model.entity.NoTrilha;
import com.itb.inf2cm.CursiFy.model.entity.ProgressoNo;
import com.itb.inf2cm.CursiFy.model.entity.Trilha;
import com.itb.inf2cm.CursiFy.model.exception.ResourceNotFoundException;
import com.itb.inf2cm.CursiFy.model.repository.ProgressoNoRepository;
import com.itb.inf2cm.CursiFy.model.services.TrilhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/api/trilhas", "/api/public/trilhas"})
public class PublicTrackController {

    @Autowired
    private TrilhaService trilhaService;

    @Autowired
    private ProgressoNoRepository progressoNoRepository;

    @GetMapping
    public List<PublicTrackResponse> list(@RequestParam(required = false) Long userId) {
        List<ProgressoNo> progresso = userId == null ? List.of() : progressoNoRepository.findByUsuarioId(userId);
        return trilhaService.findAll().stream()
                .sorted(Comparator.comparing(Trilha::getId))
                .map(trilha -> mapTrack(trilha, progresso))
                .toList();
    }

    @GetMapping("/{cursoId}")
    public PublicTrackResponse getByCourse(@PathVariable Long cursoId, @RequestParam(required = false) Long userId) {
        return list(userId).stream()
                .filter(track -> track.getId().equals(cursoId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Trilha nao encontrada para o curso " + cursoId));
    }

    private PublicTrackResponse mapTrack(Trilha trilha, List<ProgressoNo> progresso) {
        List<NoTrilha> nos = trilha.getNos() == null ? List.of() : trilha.getNos().stream().sorted(Comparator.comparing(no -> no.getOrdem() == null ? 0 : no.getOrdem())).toList();
        Map<Long, ProgressoNo> progressoPorNo = progresso.stream()
                .filter(p -> p.getNoId() != null)
                .collect(Collectors.toMap(ProgressoNo::getNoId, p -> p, (a, b) -> a));
        List<PublicTrackNodeResponse> nodes = new ArrayList<>();
        int ordemAtual = 0;
        for (NoTrilha no : nos) {
            ordemAtual++;
            ProgressoNo entry = progressoPorNo.get(no.getId());
            String estadoNode = resolveEstado(ordemAtual, entry);
            nodes.add(new PublicTrackNodeResponse(
                    no.getId(),
                    no.getTitulo(),
                    no.getTipo(),
                    estadoNode,
                    no.getXpRecompensa() == null ? 10 : no.getXpRecompensa(),
                    no.getIcone() == null ? "book" : no.getIcone(),
                    "EM_ANDAMENTO".equals(estadoNode),
                    "CHECKPOINT".equalsIgnoreCase(no.getTipo())
            ));
        }
        if (nodes.isEmpty()) {
            nodes.add(new PublicTrackNodeResponse(trilha.getId() * 100, "Visao geral", "LICAO", "EM_ANDAMENTO", 50, "book", true, false));
        }
        int xpTotal = nodes.stream().mapToInt(PublicTrackNodeResponse::getXp).sum();
        int progressoPercent = Math.min(100, nodes.size() * 20);
        int streak = Math.max(1, nodes.size());
        String proximoNo = nodes.get(0).getTitulo();
        return new PublicTrackResponse(
                trilha.getId(),
                trilha.getTitulo(),
                trilha.getMateria(),
                "Equipe CursiFy",
                xpTotal,
                resolveDifficulty(nodes.size()),
                trilha.getThumbnailUrl() == null ? "/carousel-1.jpg" : trilha.getThumbnailUrl(),
                progressoPercent,
                streak,
                proximoNo,
                nodes
        );
    }

    private String resolveEstado(int ordem, ProgressoNo entry) {
        if (entry != null && "CONCLUIDO".equalsIgnoreCase(entry.getStatus())) return "CONCLUIDO";
        if (ordem == 1) return entry == null ? "EM_ANDAMENTO" : "LIBERADO";
        return entry == null ? "LIBERADO" : "EM_ANDAMENTO";
    }

    private String resolveDifficulty(int nodeCount) {
        if (nodeCount <= 2) return "Iniciante";
        if (nodeCount <= 4) return "Intermediario";
        return "Avancado";
    }
}
