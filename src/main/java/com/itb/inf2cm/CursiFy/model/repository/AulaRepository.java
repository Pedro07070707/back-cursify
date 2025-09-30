package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
}
