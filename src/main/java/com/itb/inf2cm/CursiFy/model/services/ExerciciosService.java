package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Exercicios;
import com.itb.inf2cm.CursiFy.model.repository.ExerciciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExerciciosService {

    @Autowired
    private ExerciciosRepository exerciciosRepository;

    public List<Exercicios> findAll() {
        return exerciciosRepository.findAll();
    }

    @Transactional
    public Exercicios save(Exercicios exercicios) {
        if (exercicios.getEnunciado() == null || exercicios.getEnunciado().isBlank()) {
            throw new IllegalArgumentException("O exercício precisa ter um enunciado.");
        }
        if (exercicios.getStatusExercicios() == null || exercicios.getStatusExercicios().isBlank()) {
            exercicios.setStatusExercicios("Nao concluido");
        }
        if (exercicios.getAlternativas() == null || exercicios.getAlternativas().stream().noneMatch(value -> value != null && !value.isBlank())) {
            throw new IllegalArgumentException("O exercício precisa ter pelo menos uma alternativa.");
        }
        if (exercicios.getRespostaCorreta() == null || exercicios.getRespostaCorreta().isBlank()) {
            throw new IllegalArgumentException("O exercício precisa ter uma resposta correta.");
        }
        return exerciciosRepository.save(exercicios);
    }

    public Exercicios findById(Long id) {
        return exerciciosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercicios nao encontrado com o id " + id));
    }

    public Exercicios update(Long id, Exercicios exercicios) {
        Exercicios exerciciosExistente = findById(id);
        exerciciosExistente.setEnunciado(exercicios.getEnunciado());
        exerciciosExistente.setAlternativas(exercicios.getAlternativas());
        exerciciosExistente.setRespostaCorreta(exercicios.getRespostaCorreta());
        exerciciosExistente.setExplicacao(exercicios.getExplicacao());
        exerciciosExistente.setStatusExercicios(exercicios.getStatusExercicios() == null || exercicios.getStatusExercicios().isBlank() ? "Nao concluido" : exercicios.getStatusExercicios());
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
