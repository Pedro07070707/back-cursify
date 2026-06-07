package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.Exercicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciciosRepository extends JpaRepository<Exercicios, Long> {
    List<Exercicios> findByCurso_IdOrderByIdAsc(Long cursoId);
    long countByCurso_Id(Long cursoId);
}
