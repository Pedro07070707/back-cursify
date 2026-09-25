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

    @Query(value = "SELECT TOP 1 uc1.curso_id, c.nome FROM UsuarioCurso uc1 JOIN UsuarioCurso uc2 ON uc1.curso_id = uc2.curso_id JOIN Curso c ON c.id = uc1.curso_id WHERE uc1.usuario_id = :userA AND uc2.usuario_id = :userB", nativeQuery = true)
    Object[] findSharedCourse(@Param("userA") Long userA, @Param("userB") Long userB);

    @Query(value = "SELECT DISTINCT u.id, u.nome, u.email, u.nivel_acesso, c.nome FROM Usuario u JOIN UsuarioCurso other ON other.usuario_id = u.id JOIN UsuarioCurso mine ON mine.curso_id = other.curso_id JOIN Curso c ON c.id = mine.curso_id JOIN Usuario me ON me.id = mine.usuario_id WHERE me.id = :userId AND u.id <> :userId AND ((UPPER(me.nivel_acesso) IN ('ALUNO','STUDENT') AND UPPER(u.nivel_acesso) IN ('PROFESSOR','TEACHER')) OR (UPPER(me.nivel_acesso) IN ('PROFESSOR','TEACHER') AND UPPER(u.nivel_acesso) IN ('ALUNO','STUDENT')))", nativeQuery = true)
    List<Object[]> findChatContacts(@Param("userId") Long userId);
}
