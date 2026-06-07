package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Curso;
import com.itb.inf2cm.CursiFy.model.services.MatriculaService;
import com.itb.inf2cm.CursiFy.model.entity.Aula;
import com.itb.inf2cm.CursiFy.model.entity.Modulo;
import com.itb.inf2cm.CursiFy.model.repository.AulaRepository;
import com.itb.inf2cm.CursiFy.model.repository.ModuloRepository;
import com.itb.inf2cm.CursiFy.model.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private ModuloRepository moduloRepository;
    @Autowired
    private AulaRepository aulaRepository;
    @Autowired
    private MatriculaService matriculaService;

    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    public Curso save(Curso curso) {
        curso.setStatusCurso("Ativo");
        curso.setDataCriacao(java.time.LocalDateTime.now());

        return cursoRepository.save(curso);
    }

    public Curso findById(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Curso não encontrado como o Id" + id));
    }

    public Curso update(Long id, Curso curso) {
        Curso cursoExistente = findById(id);
        cursoExistente.setNome(curso.getNome());
        cursoExistente.setDescricao(curso.getDescricao());
        //curso.setCategoria(curso.getCategoria());
        //curso.setCargaHoraria(curso.getCargaHoraria());
        cursoExistente.setCategoria(curso.getCategoria());
        cursoExistente.setCargaHoraria(curso.getCargaHoraria());
        //cursoExistente.setPreco(curso.getPreco());
        cursoExistente.setDataCriacao(curso.getDataCriacao());
        cursoExistente.setStatusCurso(curso.getStatusCurso());
        return cursoRepository.save(cursoExistente);
    }

    public void delete(Long id) {
        Curso cursoExistente = findById(id);
        cursoRepository.delete(cursoExistente);
    }

    public Map<String, Object> detalheCurso(Long id, Long usuarioId) {
        Curso curso = findById(id);
        List<Modulo> modulosEntity = moduloRepository.findByCursoIdOrderByOrdemAsc(id);
        List<Map<String, Object>> modulos = new ArrayList<>();
        for (Modulo modulo : modulosEntity) {
            List<Map<String, Object>> aulas = new ArrayList<>();
            List<Aula> aulasEntity = aulaRepository.findByModuloIdOrderByOrdemAsc(modulo.getId());
            for (Aula aula : aulasEntity) {
                Map<String, Object> aulaMap = new java.util.HashMap<>();
                aulaMap.put("id", aula.getId());
                aulaMap.put("titulo", aula.getTitulo());
                aulaMap.put("subtitulo", aula.getTipo());
                aulaMap.put("conteudo", aula.getConteudoTexto());
                aulaMap.put("link", aula.getConteudoUrl());
                aulaMap.put("status", Boolean.TRUE.equals(aula.getGratuita()) ? "GRATUITA" : "PAGA");
                aulaMap.put("tipo", aula.getTipo());
                aulaMap.put("duracao", aula.getDuracaoMinutos());
                aulas.add(aulaMap);
            }
            Map<String, Object> moduloMap = new java.util.HashMap<>();
            moduloMap.put("id", modulo.getId());
            moduloMap.put("titulo", modulo.getTitulo());
            moduloMap.put("aulas", aulas);
            modulos.add(moduloMap);
        }

        if (modulos.isEmpty()) {
            modulos = List.of();
        }

        boolean matriculado = usuarioId != null && matriculaService.estaMatriculado(id, usuarioId);
        return Map.of(
            "id", curso.getId(),
            "nome", curso.getNome(),
            "descricao", curso.getDescricao(),
            "categoria", curso.getCategoria(),
            "cargaHoraria", curso.getCargaHoraria(),
            "statusCurso", curso.getStatusCurso(),
            "matriculado", matriculado,
            "modulos", modulos
        );
    }
}
