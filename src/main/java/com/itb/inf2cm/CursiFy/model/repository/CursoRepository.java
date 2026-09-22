package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    @Modifying
    @Transactional
    @Query("update Curso c set c.numeroAlunos = c.numeroAlunos + 1 where c.id = :cursoId and c.numeroAlunos < 100")
    int reserveStudentSpot(@Param("cursoId") Long cursoId);

    @Modifying
    @Transactional
    @Query("update Curso c set c.numeroAlunos = case when c.numeroAlunos > 0 then c.numeroAlunos - 1 else 0 end where c.id = :cursoId")
    void releaseStudentSpot(@Param("cursoId") Long cursoId);
}