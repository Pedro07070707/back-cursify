package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.dto.NoAlternativaResponse;
import com.itb.inf2cm.CursiFy.model.dto.NoQuestaoResponse;
import com.itb.inf2cm.CursiFy.model.entity.Alternativa;
import com.itb.inf2cm.CursiFy.model.entity.Questao;
import com.itb.inf2cm.CursiFy.model.repository.AlternativaRepository;
import com.itb.inf2cm.CursiFy.model.repository.QuestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoQuestaoService {
    @Autowired private QuestaoRepository questaoRepository;
    @Autowired private AlternativaRepository alternativaRepository;

    public List<NoQuestaoResponse> listarPorNo(Long noId) {
        return questaoRepository.findByNoIdOrderByOrdemAsc(noId).stream().map(this::toResponse).toList();
    }

    private NoQuestaoResponse toResponse(Questao questao) {
        List<NoAlternativaResponse> alternativas = alternativaRepository.findByQuestaoIdOrderByOrdemAsc(questao.getId())
            .stream()
            .map(this::toAlternativaResponse)
            .toList();
        return new NoQuestaoResponse(questao.getId(), questao.getEnunciado(), questao.getTipo(), questao.getExplicacao(), questao.getOrdem(), alternativas);
    }

    private NoAlternativaResponse toAlternativaResponse(Alternativa alternativa) {
        return new NoAlternativaResponse(alternativa.getId(), alternativa.getTexto(), alternativa.getOrdem());
    }
}
