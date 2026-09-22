package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Curso;
import com.itb.inf2cm.CursiFy.model.entity.UsuarioCurso;
import com.itb.inf2cm.CursiFy.model.entity.Usuario;
import com.itb.inf2cm.CursiFy.model.repository.CursoRepository;
import com.itb.inf2cm.CursiFy.model.repository.UsuarioCursoRepository;
import com.itb.inf2cm.CursiFy.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioCursoService {

    @Autowired
    private UsuarioCursoRepository usuarioCursoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioCurso> findAll() {
        return usuarioCursoRepository.findAll();
    }

    public long countStudents(Long cursoId) {
        return cursoRepository.findById(cursoId)
                .map(curso -> (long) Math.max(0, curso.getNumeroAlunos() == null ? 0 : curso.getNumeroAlunos()))
                .orElseThrow(() -> new RuntimeException("Curso nao encontrado"));
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

    public UsuarioCurso getProgress(Long usuarioId, Long cursoId) {
        Usuario usuario = getStudent(usuarioId);
        List<UsuarioCurso> existentes = usuarioCursoRepository.findByUsuarioIdAndCursoId(usuarioId, cursoId);
        if (!existentes.isEmpty()) return existentes.get(0);
        UsuarioCurso novo = new UsuarioCurso();
        novo.setUsuario(usuario);
        novo.setCurso(cursoRepository.findById(cursoId).orElseThrow());
        return novo;
    }

    public UsuarioCurso saveProgress(Long usuarioId, Long cursoId, UsuarioCurso dados) {
        UsuarioCurso atual = getProgress(usuarioId, cursoId);
        int progresso = Math.max(0, Math.min(100, dados.getProgresso() == null ? 0 : dados.getProgresso()));
        atual.setProgresso(progresso);
        atual.setConcluido(progresso >= 100);
        return usuarioCursoRepository.save(atual);
    }

    @Transactional
    public UsuarioCurso enroll(Long usuarioId, Long cursoId) {
        Usuario usuario = getStudent(usuarioId);
        List<UsuarioCurso> existentes = usuarioCursoRepository.findByUsuarioIdAndCursoId(usuarioId, cursoId);
        if (!existentes.isEmpty()) return existentes.get(0);
        Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new RuntimeException("Curso nao encontrado"));
        if (cursoRepository.reserveStudentSpot(cursoId) == 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Curso cheio");
        }
        UsuarioCurso novo = new UsuarioCurso();
        novo.setUsuario(usuario);
        novo.setCurso(curso);
        return usuarioCursoRepository.save(novo);
    }

    @Transactional
    public void removeEnrollment(Long usuarioId, Long cursoId) {
        UsuarioCurso inscricao = usuarioCursoRepository.findByUsuarioIdAndCursoId(usuarioId, cursoId).stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matricula nao encontrada"));
        getStudent(usuarioId);
        if (Boolean.TRUE.equals(inscricao.getConcluido()) || (inscricao.getProgresso() != null && inscricao.getProgresso() >= 100)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cursos concluidos nao podem ser removidos");
        }
        usuarioCursoRepository.delete(inscricao);
        cursoRepository.releaseStudentSpot(cursoId);
    }

    private Usuario getStudent(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        String role = usuario.getNivelAcesso() == null ? "" : usuario.getNivelAcesso().trim().toUpperCase();
        if (!role.equals("ALUNO") && !role.equals("STUDENT")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Somente alunos podem se inscrever e salvar progresso");
        }
        return usuario;
    }
}
