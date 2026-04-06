package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Exercicios;
import com.itb.inf2cm.CursiFy.model.repository.ExerciciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciciosService {

    @Autowired
    private ExerciciosRepository exerciciosRepository;

    public List<Exercicios> findAll() {
        return exerciciosRepository.findAll();
    }

    public Exercicios save(Exercicios exercicios) {
        if (exercicios.getStatusExercicios() == null || exercicios.getStatusExercicios().isBlank()) {
            exercicios.setStatusExercicios("Nao concluido");
        }
        return exerciciosRepository.save(exercicios);
    }

    public Exercicios findById(Long id) {
        return exerciciosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercicios nao encontrado com o id " + id));
    }

    public Exercicios update(Long id, Exercicios exercicios) {
        Exercicios exerciciosExistente = findById(id);
        exerciciosExistente.setTitulo(exercicios.getTitulo());
        exerciciosExistente.setSubtitulo(exercicios.getSubtitulo());
        exerciciosExistente.setConteudo(exercicios.getConteudo());
        exerciciosExistente.setLink(exercicios.getLink());
        exerciciosExistente.setStatusExercicios(exercicios.getStatusExercicios());
        if (exercicios.getUsuario() != null) {
            exerciciosExistente.setUsuario(exercicios.getUsuario());
        }
        if (exercicios.getCurso() != null) {
            exerciciosExistente.setCurso(exercicios.getCurso());
        }
        return exerciciosRepository.save(exerciciosExistente);
    }

    public void delete(Long id) {
        Exercicios exerciciosExistente = findById(id);
        exerciciosRepository.delete(exerciciosExistente);
    }
}
