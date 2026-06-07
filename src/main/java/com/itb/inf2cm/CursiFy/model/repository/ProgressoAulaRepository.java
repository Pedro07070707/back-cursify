package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.ProgressoAula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ProgressoAulaRepository extends JpaRepository<ProgressoAula, Long> {
    Optional<ProgressoAula> findByUsuarioIdAndAulaId(Long usuarioId, Long aulaId);
    long countByUsuarioIdAndAulaIdInAndConcluidaTrue(Long usuarioId, Collection<Long> aulaIds);
}
