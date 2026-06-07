package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.NoTrilha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoTrilhaRepository extends JpaRepository<NoTrilha, Long> {
    List<NoTrilha> findByTrilha_IdOrderByOrdemAsc(Long trilhaId);
    Optional<NoTrilha> findFirstByTrilha_IdOrderByOrdemAsc(Long trilhaId);
}
