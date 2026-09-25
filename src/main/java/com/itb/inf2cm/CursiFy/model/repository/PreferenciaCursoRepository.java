package com.itb.inf2cm.CursiFy.model.repository;
import com.itb.inf2cm.CursiFy.model.entity.PreferenciaCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface PreferenciaCursoRepository extends JpaRepository<PreferenciaCurso, Long> {
    Optional<PreferenciaCurso> findByUsuarioIdAndCursoIdAndTipo(Long usuarioId, Long cursoId, String tipo);
    List<PreferenciaCurso> findByUsuarioIdAndTipo(Long usuarioId, String tipo);
}
