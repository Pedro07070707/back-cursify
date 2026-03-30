package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Avaliacao;
import com.itb.inf2cm.CursiFy.model.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository AvaliacaoRepository;

    public List<Avaliacao> findAll() {
        return AvaliacaoRepository.findAll();
    }

    public Avaliacao save(Avaliacao Avaliacao) {
        Avaliacao.setStatusAvaliacao(0);
        return AvaliacaoRepository.save(Avaliacao);
    }

    public Avaliacao findById(Long id) {
        return AvaliacaoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado como o Id" + id));
    }

    public Avaliacao update(Long id, Avaliacao Avaliacao) {
        Avaliacao AvaliacaoExistente = findById(id);
        AvaliacaoExistente.setNumeroAvaliacao(Avaliacao.getNumeroAvaliacao());
        AvaliacaoExistente.setEnunciadoAvaliacao(Avaliacao.getEnunciadoAvaliacao());
        AvaliacaoExistente.setAlternativaAvaliacao(Avaliacao.getAlternativaAvaliacao());
        AvaliacaoExistente.setStatusAvaliacao(Avaliacao.getStatusAvaliacao());
        return AvaliacaoRepository.save(AvaliacaoExistente);
    }

    public void delete(Long id) {
        Avaliacao AvaliacaoExistente = findById(id);
        AvaliacaoRepository.delete(AvaliacaoExistente);
    }
}
