package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.dto.ConcluirAulaResponse;
import com.itb.inf2cm.CursiFy.model.entity.ProgressoAula;
import com.itb.inf2cm.CursiFy.model.repository.ProgressoAulaRepository;
import com.itb.inf2cm.CursiFy.model.services.CertificadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProgressoAulaService {
    @Autowired private ProgressoAulaRepository progressoAulaRepository;
    @Autowired private CertificadoService certificadoService;

    public ConcluirAulaResponse concluirAula(Long aulaId, Long usuarioId, Long cursoId) {
        ProgressoAula progresso = progressoAulaRepository.findByUsuarioIdAndAulaId(usuarioId, aulaId).orElseGet(ProgressoAula::new);
        progresso.setUsuarioId(usuarioId);
        progresso.setAulaId(aulaId);
        progresso.setConcluida(Boolean.TRUE);
        progresso.setDataConclusao(LocalDateTime.now());
        progressoAulaRepository.save(progresso);
        ConcluirAulaResponse response = new ConcluirAulaResponse(aulaId, usuarioId, "CONCLUIDA");
        if (cursoId != null) {
            response.setCertificado(certificadoService.emitirSeCursoConcluido(usuarioId, cursoId));
            response.setCertificadoLiberado(response.getCertificado() != null);
        }
        return response;
    }
}
