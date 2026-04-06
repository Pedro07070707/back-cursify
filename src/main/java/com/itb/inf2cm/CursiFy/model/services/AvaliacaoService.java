package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Avaliacao;
import com.itb.inf2cm.CursiFy.model.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public List<Avaliacao> findAll() {
        return avaliacaoRepository.findAll();
    }

    public Avaliacao save(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public Avaliacao findById(Long id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliacao nao encontrada com o id " + id));
    }

    public Avaliacao update(Long id, Avaliacao avaliacao) {
        Avaliacao avaliacaoExistente = findById(id);
        avaliacaoExistente.setNumeroAvaliacao(avaliacao.getNumeroAvaliacao());
        avaliacaoExistente.setEnunciadoAvaliacao(avaliacao.getEnunciadoAvaliacao());
        avaliacaoExistente.setAlternativaAvaliacao(avaliacao.getAlternativaAvaliacao());
        avaliacaoExistente.setStatusAvaliacao(avaliacao.getStatusAvaliacao());
        if (avaliacao.getUsuario() != null) {
            avaliacaoExistente.setUsuario(avaliacao.getUsuario());
        }
        if (avaliacao.getCurso() != null) {
            avaliacaoExistente.setCurso(avaliacao.getCurso());
        }
        return avaliacaoRepository.save(avaliacaoExistente);
    }

    public void delete(Long id) {
        Avaliacao avaliacaoExistente = findById(id);
        avaliacaoRepository.delete(avaliacaoExistente);
    }
}
