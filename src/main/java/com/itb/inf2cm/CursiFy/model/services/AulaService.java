package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Aula;
import com.itb.inf2cm.CursiFy.model.repository.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    public List<Aula> findAll() {
        return aulaRepository.findAll();
    }

    public Aula save(Aula aula) {
        aula.setStatusAula(true);
        return aulaRepository.save(aula);
    }

    public Aula findById(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Aula não encontrada como o Id" + id));
    }

    public Aula update(Long id, Aula aula) {
        Aula aulaExistente = findById(id);
        aulaExistente.setTitulo(aula.getTitulo());
        aulaExistente.setDescricao(aula.getDescricao());
        aulaExistente.setDuracao(aula.getDuracao());
        aulaExistente.setConteudo(aula.getConteudo());
        aulaExistente.setLink(aula.getLink());
        aulaExistente.setStatusAula(aula.getStatusAula());
        return aulaRepository.save(aulaExistente);
    }

    public void delete(Long id) {
        Aula aulaExistente = findById(id);
        aulaRepository.delete(aulaExistente);
    }
}
