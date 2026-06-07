package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.dto.CertificadoResponse;
import com.itb.inf2cm.CursiFy.model.entity.Certificado;
import com.itb.inf2cm.CursiFy.model.entity.Aula;
import com.itb.inf2cm.CursiFy.model.entity.Modulo;
import com.itb.inf2cm.CursiFy.model.repository.CertificadoRepository;
import com.itb.inf2cm.CursiFy.model.repository.AulaRepository;
import com.itb.inf2cm.CursiFy.model.repository.ModuloRepository;
import com.itb.inf2cm.CursiFy.model.repository.MatriculaRepository;
import com.itb.inf2cm.CursiFy.model.repository.ProgressoAulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CertificadoService {
    @Autowired private CertificadoRepository certificadoRepository;
    @Autowired private MatriculaRepository matriculaRepository;
    @Autowired private ModuloRepository moduloRepository;
    @Autowired private AulaRepository aulaRepository;
    @Autowired private ProgressoAulaRepository progressoAulaRepository;

    public CertificadoResponse emitirSeMatriculado(Long usuarioId, Long cursoId) {
        if (matriculaRepository.findByUsuarioIdAndCursoId(usuarioId, cursoId).isEmpty()) {
            return null;
        }

        Certificado certificado = certificadoRepository.findByUsuarioIdAndCursoId(usuarioId, cursoId).orElseGet(Certificado::new);
        if (certificado.getCodigoValidacao() == null) {
            certificado.setUsuarioId(usuarioId);
            certificado.setCursoId(cursoId);
            certificado.setCodigoValidacao("CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            certificado.setEmitidoEm(LocalDateTime.now());
            certificado = certificadoRepository.save(certificado);
        }
        return new CertificadoResponse(certificado.getCursoId(), certificado.getUsuarioId(), certificado.getCodigoValidacao(), certificado.getEmitidoEm());
    }

    public CertificadoResponse emitirSeCursoConcluido(Long usuarioId, Long cursoId) {
        if (matriculaRepository.findByUsuarioIdAndCursoId(usuarioId, cursoId).isEmpty()) {
            return null;
        }

        List<Long> aulaIds = moduloRepository.findByCursoIdOrderByOrdemAsc(cursoId).stream()
            .map(Modulo::getId)
            .flatMap(moduloId -> aulaRepository.findByModuloIdOrderByOrdemAsc(moduloId).stream().map(Aula::getId))
            .collect(Collectors.toList());

        if (aulaIds.isEmpty()) {
            return null;
        }

        long concluidas = progressoAulaRepository.countByUsuarioIdAndAulaIdInAndConcluidaTrue(usuarioId, aulaIds);
        if (concluidas < aulaIds.size()) {
            return null;
        }

        return emitirSeMatriculado(usuarioId, cursoId);
    }
}
