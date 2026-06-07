package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.ParticipanteConversa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipanteConversaRepository extends JpaRepository<ParticipanteConversa, Long> {
    List<ParticipanteConversa> findByUsuarioId(Long usuarioId);
}
