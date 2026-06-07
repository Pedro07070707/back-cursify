package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.dto.MatriculaResponse;
import com.itb.inf2cm.CursiFy.model.entity.Matricula;
import com.itb.inf2cm.CursiFy.model.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MatriculaService {
    @Autowired private MatriculaRepository matriculaRepository;

    public MatriculaResponse matricular(Long cursoId, Long usuarioId) {
        Matricula matricula = matriculaRepository.findByUsuarioIdAndCursoId(usuarioId, cursoId).orElseGet(Matricula::new);
        matricula.setCursoId(cursoId);
        matricula.setUsuarioId(usuarioId);
        matricula.setConcluido(Boolean.FALSE);
        if (matricula.getDataMatricula() == null) {
            matricula.setDataMatricula(LocalDateTime.now());
        }
        matricula = matriculaRepository.save(matricula);
        return new MatriculaResponse(cursoId, usuarioId, "MATRICULADO");
    }

    public boolean estaMatriculado(Long cursoId, Long usuarioId) {
        return matriculaRepository.findByUsuarioIdAndCursoId(usuarioId, cursoId).isPresent();
    }
}
