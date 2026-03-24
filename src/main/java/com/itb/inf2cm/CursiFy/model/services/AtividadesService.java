package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Atividades;
import com.itb.inf2cm.CursiFy.model.repository.AtividadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtividadesService {

    @Autowired
    private AtividadesRepository AtividadesRepository;

    public List<Atividades> findAll() {
        return AtividadesRepository.findAll();
    }

    public Atividades save(Atividades Atividades) {
        Atividades.setStatusAtividade(true);
        return AtividadesRepository.save(Atividades);
    }

    public Atividades findById(Long id) {
        return AtividadesRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado como o Id" + id));
    }

    public Atividades update(Long id, Atividades Atividades) {
        Atividades AtividadesExistente = findById(id);
        AtividadesExistente.setNumeroAtividade(Atividades.getNumeroAtividade());
        AtividadesExistente.setEnunciadoAtividade(Atividades.getEnunciadoAtividade());
        AtividadesExistente.setAlternativaAtividade(Atividades.getAlternativaAtividade());
        AtividadesExistente.setStatusAtividade(Atividades.getStatusAtividade());
        return AtividadesRepository.save(AtividadesExistente);
    }

    public void delete(Long id) {
        Atividades AtividadesExistente = findById(id);
        AtividadesRepository.delete(AtividadesExistente);
    }
}
