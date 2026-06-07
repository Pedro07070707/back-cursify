package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.ProjetoEnviado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjetoEnviadoRepository extends JpaRepository<ProjetoEnviado, Long> {
    List<ProjetoEnviado> findByUsuarioIdAndNoIdOrderByEnviadoEmDesc(Long usuarioId, Long noId);
    Optional<ProjetoEnviado> findTopByUsuarioIdAndNoIdOrderByEnviadoEmDesc(Long usuarioId, Long noId);
    List<ProjetoEnviado> findByStatusOrderByEnviadoEmDesc(String status);
}
