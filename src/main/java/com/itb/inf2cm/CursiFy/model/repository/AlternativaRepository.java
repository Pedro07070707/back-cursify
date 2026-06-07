package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.Alternativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlternativaRepository extends JpaRepository<Alternativa, Long> {
    List<Alternativa> findByQuestaoIdOrderByOrdemAsc(Long questaoId);
    Optional<Alternativa> findByIdAndQuestaoId(Long id, Long questaoId);
}
