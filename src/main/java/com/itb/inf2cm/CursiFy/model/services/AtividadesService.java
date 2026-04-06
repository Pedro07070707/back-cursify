package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Atividades;
import com.itb.inf2cm.CursiFy.model.repository.AtividadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtividadesService {

    @Autowired
    private AtividadesRepository atividadesRepository;

    public List<Atividades> findAll() {
        return atividadesRepository.findAll();
    }

    public Atividades save(Atividades atividades) {
        return atividadesRepository.save(atividades);
    }

    public Atividades findById(Long id) {
        return atividadesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade nao encontrada com o id " + id));
    }

    public Atividades update(Long id, Atividades atividades) {
        Atividades atividadesExistente = findById(id);
        atividadesExistente.setNumeroAtividade(atividades.getNumeroAtividade());
        atividadesExistente.setEnunciadoAtividade(atividades.getEnunciadoAtividade());
        atividadesExistente.setAlternativaAtividade(atividades.getAlternativaAtividade());
        atividadesExistente.setStatusAtividade(atividades.getStatusAtividade());
        if (atividades.getUsuario() != null) {
            atividadesExistente.setUsuario(atividades.getUsuario());
        }
        if (atividades.getCurso() != null) {
            atividadesExistente.setCurso(atividades.getCurso());
        }
        return atividadesRepository.save(atividadesExistente);
    }

    public void delete(Long id) {
        Atividades atividadesExistente = findById(id);
        atividadesRepository.delete(atividadesExistente);
    }
}
