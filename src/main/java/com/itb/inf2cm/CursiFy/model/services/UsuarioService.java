package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Usuario;
import com.itb.inf2cm.CursiFy.model.repository.ExerciciosRepository;
import com.itb.inf2cm.CursiFy.model.repository.MaterialRepository;
import com.itb.inf2cm.CursiFy.model.repository.UsuarioCursoRepository;
import com.itb.inf2cm.CursiFy.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioCursoRepository usuarioCursoRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ExerciciosRepository exerciciosRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        if ("ADMIN".equalsIgnoreCase(usuario.getNivelAcesso()) && !"260926".equals(usuario.getCodigoAdmin())) {
            throw new IllegalArgumentException("Código de administrador inválido.");
        }
        validarCpf(usuario);
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new IllegalArgumentException("Este CPF já está cadastrado.");
        }
        if (usuario.getStatusUsuario() == null || usuario.getStatusUsuario().isBlank()) {
            usuario.setStatusUsuario("Ativo");
        }
        // Nunca confiar no horario enviado pelo navegador (normalmente UTC).
        usuario.setDataCadastro(com.itb.inf2cm.CursiFy.config.ClockConfig.now());
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o Id" + id));
    }

    public Usuario saveTheme(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, Usuario usuario) {
        Usuario usuarioExistente = findById(id);
        validarCpf(usuario);
        if (usuarioRepository.existsByCpfAndIdNot(usuario.getCpf(), id)) {
            throw new IllegalArgumentException("Este CPF já está cadastrado.");
        }
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        atualizarSenha(usuarioExistente, usuario.getSenha());
        usuarioExistente.setCpf(usuario.getCpf());
        String requestedRole = usuario.getNivelAcesso() == null ? "ALUNO" : usuario.getNivelAcesso().trim().toUpperCase();
        boolean switchingToStudent = "ALUNO".equals(requestedRole) || "STUDENT".equals(requestedRole);
        boolean requestingTeacher = "PROFESSOR".equals(requestedRole) || "TEACHER".equals(requestedRole);
        boolean approvedTeacher = "Aprovado".equalsIgnoreCase(usuarioExistente.getProfessorAprovado())
                || "Aprovado".equalsIgnoreCase(usuario.getProfessorAprovado());
        if (switchingToStudent) {
            usuarioExistente.setNivelAcesso("ALUNO");
            usuarioExistente.setProfessorAprovado("Pendente");
        } else if (requestingTeacher && approvedTeacher) {
            usuarioExistente.setNivelAcesso("PROFESSOR");
            usuarioExistente.setProfessorAprovado("Aprovado");
        } else if (requestingTeacher) {
            usuarioExistente.setNivelAcesso("ALUNO");
            usuarioExistente.setProfessorAprovado("Pendente");
        } else {
            usuarioExistente.setNivelAcesso(usuario.getNivelAcesso());
        }
        usuarioExistente.setFoto(usuario.getFoto());
        usuarioExistente.setBio(usuario.getBio());
        usuarioExistente.setFotoCapa(usuario.getFotoCapa());
        usuarioExistente.setTemaPreferido(usuario.getTemaPreferido());
        /* O administrador altera esta flag ao aprovar/rejeitar um professor. */
        usuarioExistente.setProfessorAprovado(usuario.getProfessorAprovado());
        usuarioExistente.setStatusUsuario(normalizarStatus(usuario.getStatusUsuario()));
        return usuarioRepository.save(usuarioExistente);
    }

    public void delete(Long id) {
        materialRepository.deleteByUsuarioIdNative(id);
        exerciciosRepository.deleteByUsuarioIdNative(id);
        usuarioCursoRepository.deleteByUsuarioIdNative(id);
        usuarioRepository.delete(findById(id));
    }

    public Optional<Usuario> login(String email, String senhaPlana) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(senhaPlana, u.getSenha()));
    }

    private void validarCpf(Usuario usuario) {
        if (usuario.getCpf() == null || !usuario.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("O CPF deve conter exatamente 11 digitos.");
        }
    }

    private void atualizarSenha(Usuario usuarioExistente, String senhaRecebida) {
        if (senhaRecebida == null || senhaRecebida.isBlank()) {
            return;
        }

        if (senhaRecebida.startsWith("$2a$") || senhaRecebida.startsWith("$2b$") || senhaRecebida.startsWith("$2y$")) {
            usuarioExistente.setSenha(senhaRecebida);
            return;
        }

        usuarioExistente.setSenha(passwordEncoder.encode(senhaRecebida));
    }

    private String normalizarStatus(String statusUsuario) {
        if (statusUsuario == null || statusUsuario.isBlank()) {
            return "Ativo";
        }

        if ("true".equalsIgnoreCase(statusUsuario) || "Ativo".equalsIgnoreCase(statusUsuario)) {
            return "Ativo";
        }

        if ("false".equalsIgnoreCase(statusUsuario) || "Inativo".equalsIgnoreCase(statusUsuario)) {
            return "Inativo";
        }

        return statusUsuario;
    }
}
