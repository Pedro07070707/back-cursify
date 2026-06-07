package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversaRepository extends JpaRepository<Conversa, Long> {
    List<Conversa> findByTrilhaId(Long trilhaId);
}
