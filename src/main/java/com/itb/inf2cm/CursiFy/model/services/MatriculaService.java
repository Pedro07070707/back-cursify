package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    public List<Matricula> findAll() {
        return matriculaRepository.findAll();
    }

    public Matricula save(Matricula matricula) {
        matricula.setStatusMatricula(true);
        return matriculaRepository.save(matricula);
    }

    public Matricula findById(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Matrícula não encontrada como o Id" + id));
    }

    public Matricula update(Long id, Matricula matricula) {
        Matricula matriculaExistente = findById(id);
        matricula.setDataMatricula(matricula.getDataMatricula());
        matriculaExistente.setStatusMatricula(matricula.getStatusMatricula());
        return matriculaRepository.save(matriculaExistente);
    }

    public void delete(Long id) {
        Matricula matriculaExistente = findById(id);
        matriculaRepository.delete(matriculaExistente);
    }
}
