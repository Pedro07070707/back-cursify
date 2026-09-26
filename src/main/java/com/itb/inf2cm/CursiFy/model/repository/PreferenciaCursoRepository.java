package com.itb.inf2cm.CursiFy.model.repository;
import com.itb.inf2cm.CursiFy.model.entity.PreferenciaCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface PreferenciaCursoRepository extends JpaRepository<PreferenciaCurso, Long> {
    Optional<PreferenciaCurso> findByUsuarioIdAndCursoIdAndTipo(Long usuarioId, Long cursoId, String tipo);
    List<PreferenciaCurso> findByUsuarioIdAndTipo(Long usuarioId, String tipo);

    @Query("select p from PreferenciaCurso p where p.cursoId = :cursoId and p.tipo = :tipo")
    List<PreferenciaCurso> findByCursoIdAndTipo(@Param("cursoId") Long cursoId, @Param("tipo") String tipo);
}
