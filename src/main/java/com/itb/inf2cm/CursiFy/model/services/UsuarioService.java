package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Usuario;
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
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        validarCpf(usuario);
        usuario.setStatusUsuario("Ativo");
        if (usuario.getDataCadastro() == null) {
            usuario.setDataCadastro(LocalDateTime.now());
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o Id" + id));
    }

    public Usuario update(Long id, Usuario usuario) {
        Usuario usuarioExistente = findById(id);
        validarCpf(usuario);
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        atualizarSenha(usuarioExistente, usuario.getSenha());
        usuarioExistente.setCpf(usuario.getCpf());
        usuarioExistente.setNivelAcesso(usuario.getNivelAcesso());
        usuarioExistente.setStatusUsuario(normalizarStatus(usuario.getStatusUsuario()));
        return usuarioRepository.save(usuarioExistente);
    }

    public void delete(Long id) {
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
