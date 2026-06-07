package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.ProgressoNo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ProgressoNoRepository extends JpaRepository<ProgressoNo, Long> {
    Optional<ProgressoNo> findByUsuarioIdAndNoId(Long usuarioId, Long noId);
    List<ProgressoNo> findByUsuarioId(Long usuarioId);
}
