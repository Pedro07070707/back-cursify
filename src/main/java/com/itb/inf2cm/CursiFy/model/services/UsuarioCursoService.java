package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Curso;
import com.itb.inf2cm.CursiFy.model.entity.UsuarioCurso;
import com.itb.inf2cm.CursiFy.model.repository.CursoRepository;
import com.itb.inf2cm.CursiFy.model.repository.UsuarioCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioCursoService {

    @Autowired
    private UsuarioCursoRepository usuarioCursoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public List<UsuarioCurso> findAll() {
        return usuarioCursoRepository.findAll();
    }

    public List<Curso> findCursosByProfessor(Long usuarioId) {
        List<Object[]> rows = usuarioCursoRepository.findCursosByUsuarioIdNative(usuarioId);
        return rows.stream().map(row -> {
            Long cursoId = ((Number) row[0]).longValue();
            return cursoRepository.findById(cursoId).orElse(null);
        }).filter(c -> c != null).collect(Collectors.toList());
    }

    public UsuarioCurso findById(Long id) {
        return usuarioCursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UsuarioCurso não encontrado com o Id" + id));
    }

    public UsuarioCurso update(Long id, UsuarioCurso usuarioCurso) {
        UsuarioCurso existente = findById(id);
        return usuarioCursoRepository.save(existente);
    }

    public void delete(Long id) {
        usuarioCursoRepository.delete(findById(id));
    }
}
