package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Curso;
import com.itb.inf2cm.CursiFy.model.repository.AtividadesRepository;
import com.itb.inf2cm.CursiFy.model.repository.CursoRepository;
import com.itb.inf2cm.CursiFy.model.repository.ExerciciosRepository;
import com.itb.inf2cm.CursiFy.model.repository.MaterialRepository;
import com.itb.inf2cm.CursiFy.model.repository.UsuarioCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioCursoRepository usuarioCursoRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ExerciciosRepository exerciciosRepository;

    @Autowired
    private AtividadesRepository atividadesRepository;

    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    public Curso save(Curso curso) {
        curso.setStatusCurso("Ativo");
        curso.setDataCriacao(java.time.LocalDateTime.now());
        Curso saved = cursoRepository.save(curso);

        if (curso.getProfessorId() != null) {
            usuarioCursoRepository.insertNative(curso.getProfessorId(), saved.getId());
        }

        return saved;
    }

    public Curso findById(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Curso não encontrado como o Id" + id));
    }

    public Curso update(Long id, Curso curso) {
        Curso cursoExistente = findById(id);
        cursoExistente.setNome(curso.getNome());
        cursoExistente.setDescricao(curso.getDescricao());
        cursoExistente.setCategoria(curso.getCategoria());
        cursoExistente.setCargaHoraria(curso.getCargaHoraria());
        cursoExistente.setDataCriacao(curso.getDataCriacao());
        cursoExistente.setStatusCurso(curso.getStatusCurso());
        return cursoRepository.save(cursoExistente);
    }

    public void delete(Long id) {
        materialRepository.deleteByCursoIdNative(id);
        exerciciosRepository.deleteByCursoIdNative(id);
        atividadesRepository.deleteByCursoIdNative(id);
        usuarioCursoRepository.deleteByCursoIdNative(id);
        cursoRepository.delete(findById(id));
    }
}
