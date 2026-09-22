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

    long countByCursoId(Long cursoId);

    List<UsuarioCurso> findAllByCursoId(Long cursoId);

    @Query(value = "SELECT COUNT(*) FROM UsuarioCurso uc JOIN Usuario u ON u.id = uc.usuario_id WHERE uc.curso_id = :cursoId AND UPPER(u.nivel_acesso) IN ('ALUNO','STUDENT')", nativeQuery = true)
    long countStudentsByCursoId(@Param("cursoId") Long cursoId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM UsuarioCurso WHERE curso_id = :cursoId", nativeQuery = true)
    void deleteByCursoIdNative(@Param("cursoId") Long cursoId);

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

    @Query("select uc from UsuarioCurso uc where uc.usuario.id = :usuarioId and uc.curso.id = :cursoId")
    List<UsuarioCurso> findByUsuarioIdAndCursoId(@Param("usuarioId") Long usuarioId, @Param("cursoId") Long cursoId);
}
