package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.dto.PublicTrackNodeResponse;
import com.itb.inf2cm.CursiFy.model.dto.PublicTrackResponse;
import com.itb.inf2cm.CursiFy.model.entity.Curso;
import com.itb.inf2cm.CursiFy.model.entity.Exercicios;
import com.itb.inf2cm.CursiFy.model.entity.Material;
import com.itb.inf2cm.CursiFy.model.exception.ResourceNotFoundException;
import com.itb.inf2cm.CursiFy.model.repository.ExerciciosRepository;
import com.itb.inf2cm.CursiFy.model.repository.MaterialRepository;
import com.itb.inf2cm.CursiFy.model.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public/trilhas")
public class PublicTrackController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ExerciciosRepository exerciciosRepository;

    @GetMapping
    public List<PublicTrackResponse> list() {
        List<Curso> cursos = cursoService.findAll();
        List<Material> materiais = materialRepository.findAll();
        List<Exercicios> exercicios = exerciciosRepository.findAll();

        Map<Long, List<Material>> materiaisPorCurso = materiais.stream()
                .filter(material -> material.getCurso() != null && material.getCurso().getId() != null)
                .collect(Collectors.groupingBy(material -> material.getCurso().getId()));

        Map<Long, List<Exercicios>> exerciciosPorCurso = exercicios.stream()
                .filter(exercicio -> exercicio.getCurso() != null && exercicio.getCurso().getId() != null)
                .collect(Collectors.groupingBy(exercicio -> exercicio.getCurso().getId()));

        return cursos.stream()
                .sorted(Comparator.comparing(Curso::getId))
                .map(curso -> {
                    List<Material> cursoMateriais = materiaisPorCurso.getOrDefault(curso.getId(), List.of());
                    List<Exercicios> cursoExercicios = exerciciosPorCurso.getOrDefault(curso.getId(), List.of());
                    List<PublicTrackNodeResponse> nodes = buildNodes(curso, cursoMateriais, cursoExercicios);
                    int xpTotal = nodes.stream().mapToInt(PublicTrackNodeResponse::getXp).sum();
                    int progresso = Math.min(100, (cursoMateriais.size() * 22) + (cursoExercicios.size() * 18));
                    int streak = Math.max(1, cursoMateriais.size() + cursoExercicios.size() / 2);
                    String proximoNo = nodes.stream()
                            .filter(node -> "EM_ANDAMENTO".equals(node.getEstado()) || "LIBERADO".equals(node.getEstado()))
                            .findFirst()
                            .map(PublicTrackNodeResponse::getTitulo)
                            .orElse(nodes.isEmpty() ? "Novo nodo" : nodes.get(0).getTitulo());

                    return new PublicTrackResponse(
                            curso.getId(),
                            curso.getNome(),
                            curso.getCategoria(),
                            resolveProfessorName(curso, cursoMateriais, cursoExercicios),
                            xpTotal,
                            resolveDifficulty(cursoMateriais.size() + cursoExercicios.size()),
                            pickThumbnail(curso.getCategoria()),
                            progresso,
                            streak,
                            proximoNo,
                            nodes
                    );
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{cursoId}")
    public PublicTrackResponse getByCourse(@PathVariable Long cursoId) {
        return list().stream()
                .filter(track -> track.getId().equals(cursoId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Trilha nao encontrada para o curso " + cursoId));
    }

    private List<PublicTrackNodeResponse> buildNodes(Curso curso, List<Material> materiais, List<Exercicios> exercicios) {
        List<PublicTrackNodeResponse> nodes = new ArrayList<>();

        int index = 1;
        for (Material material : materiais) {
            nodes.add(new PublicTrackNodeResponse(
                    curso.getId() * 100 + index,
                    material.getTitulo(),
                    "LICAO",
                    index == 1 ? "EM_ANDAMENTO" : "LIBERADO",
                    50 + (index * 10),
                    "book",
                    index == 1,
                    false
            ));
            index++;
        }

        for (Exercicios exercicio : exercicios) {
            nodes.add(new PublicTrackNodeResponse(
                    curso.getId() * 100 + index,
                    exercicio.getTitulo(),
                    "EXERCICIO",
                    index % 3 == 0 ? "BLOQUEADO" : "LIBERADO",
                    70 + (index * 10),
                    "pencil",
                    index == 2,
                    index % 4 == 0
            ));
            index++;
        }

        if (nodes.isEmpty()) {
            nodes.add(new PublicTrackNodeResponse(
                    curso.getId() * 100 + 1,
                    "Visao geral",
                    "LICAO",
                    "EM_ANDAMENTO",
                    50,
                    "book",
                    true,
                    false
            ));
        }

        return nodes;
    }

    private String resolveProfessorName(Curso curso, List<Material> materiais, List<Exercicios> exercicios) {
        return materiais.stream()
                .map(Material::getUsuario)
                .filter(usuario -> usuario != null && usuario.getNome() != null)
                .map(usuario -> usuario.getNome())
                .findFirst()
                .orElseGet(() -> exercicios.stream()
                        .map(Exercicios::getUsuario)
                        .filter(usuario -> usuario != null && usuario.getNome() != null)
                        .map(usuario -> usuario.getNome())
                        .findFirst()
                        .orElse("Equipe CursiFy"));
    }

    private String resolveDifficulty(int nodeCount) {
        if (nodeCount <= 2) {
            return "Iniciante";
        }
        if (nodeCount <= 4) {
            return "Intermediario";
        }
        return "Avancado";
    }

    private String pickThumbnail(String category) {
        String normalized = category == null ? "" : category.toLowerCase(Locale.ROOT);
        if (normalized.contains("ling")) return "/carousel-2.jpg";
        if (normalized.contains("tec")) return "/carousel-3.jpg";
        return "/carousel-1.jpg";
    }
}
