package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Curso;
import com.itb.inf2cm.CursiFy.model.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

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
        cursoExistente.setLink1(curso.getLink1());
        cursoExistente.setNomeLink1(curso.getNomeLink1());
        cursoExistente.setLink2(curso.getLink2());
        cursoExistente.setNomeLink2(curso.getNomeLink2());
        cursoExistente.setLink3(curso.getLink3());
        cursoExistente.setNomeLink3(curso.getNomeLink3());
        return cursoRepository.save(cursoExistente);
    }

    public void delete(Long id) {
        Curso cursoExistente = findById(id);
        cursoRepository.delete(cursoExistente);
    }
}
