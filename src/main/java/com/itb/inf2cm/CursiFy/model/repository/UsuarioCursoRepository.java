package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.UsuarioCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UsuarioCursoRepository extends JpaRepository<UsuarioCurso, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM UsuarioCurso WHERE usuario_id = :usuarioId", nativeQuery = true)
    void deleteByUsuarioIdNative(@Param("usuarioId") Long usuarioId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO UsuarioCurso (usuario_id, curso_id) VALUES (:usuarioId, :cursoId)", nativeQuery = true)
    void insertNative(@Param("usuarioId") Long usuarioId, @Param("cursoId") Long cursoId);

    @Query(value = "SELECT c.* FROM UsuarioCurso uc JOIN Curso c ON uc.curso_id = c.id WHERE uc.usuario_id = :usuarioId", nativeQuery = true)
    List<Object[]> findCursosByUsuarioIdNative(@Param("usuarioId") Long usuarioId);
}
