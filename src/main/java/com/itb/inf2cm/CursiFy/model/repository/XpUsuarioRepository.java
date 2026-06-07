package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.XpUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface XpUsuarioRepository extends JpaRepository<XpUsuario, Long> {
    Optional<XpUsuario> findByUsuarioId(Long usuarioId);
}
