package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Usuario;
import com.itb.inf2cm.CursiFy.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        if (usuario.getDataCadastro() == null) {
            usuario.setDataCadastro(LocalDateTime.now());
        }
        if (usuario.getNivelAcesso() == null || usuario.getNivelAcesso().isBlank()) {
            usuario.setNivelAcesso("USUARIO");
        }
        if (usuario.getStatusUsuario() == null || usuario.getStatusUsuario().isBlank()) {
            usuario.setStatusUsuario("Ativo");
        }
        if (usuario.getSenha() != null && !usuario.getSenha().startsWith("$2")) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario saveRaw(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o Id " + id));
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + email));
    }

    public Usuario update(Long id, Usuario usuario) {
        Usuario usuarioExistente = findById(id);
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        if (usuario.getNivelAcesso() != null) {
            usuarioExistente.setNivelAcesso(usuario.getNivelAcesso());
        }
        if (usuario.getDataCadastro() != null) {
            usuarioExistente.setDataCadastro(usuario.getDataCadastro());
        }
        if (usuario.getStatusUsuario() != null) {
            usuarioExistente.setStatusUsuario(usuario.getStatusUsuario());
        }
        return usuarioRepository.save(usuarioExistente);
    }

    public Usuario updateProfile(Long id, Usuario usuario) {
        Usuario usuarioExistente = findById(id);
        if (usuario.getNome() != null && !usuario.getNome().isBlank()) {
            usuarioExistente.setNome(usuario.getNome());
        }
        if (usuario.getEmail() != null && !usuario.getEmail().isBlank()) {
            usuarioExistente.setEmail(usuario.getEmail());
        }
        return usuarioRepository.save(usuarioExistente);
    }

    public Usuario changePassword(Long id, String senhaAtual, String novaSenha) {
        Usuario usuarioExistente = findById(id);
        boolean senhaValida = passwordEncoder.matches(senhaAtual, usuarioExistente.getSenha())
                || senhaAtual.equals(usuarioExistente.getSenha());
        if (!senhaValida) {
            throw new RuntimeException("Senha atual inválida");
        }
        usuarioExistente.setSenha(passwordEncoder.encode(novaSenha));
        return usuarioRepository.save(usuarioExistente);
    }

    public void delete(Long id) {
        Usuario usuarioExistente = findById(id);
        usuarioRepository.delete(usuarioExistente);
    }
}
