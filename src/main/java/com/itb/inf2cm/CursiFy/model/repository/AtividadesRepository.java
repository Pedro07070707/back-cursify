package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.Atividades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AtividadesRepository extends JpaRepository<Atividades, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Atividades WHERE curso_id = :cursoId", nativeQuery = true)
    void deleteByCursoIdNative(@Param("cursoId") Long cursoId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Atividades WHERE usuario_id = :usuarioId", nativeQuery = true)
    void deleteByUsuarioIdNative(@Param("usuarioId") Long usuarioId);
}
