package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.RecuperacaoSenha;
import com.itb.inf2cm.CursiFy.model.entity.Usuario;
import com.itb.inf2cm.CursiFy.model.repository.RecuperacaoSenhaRepository;
import com.itb.inf2cm.CursiFy.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RecuperacaoSenhaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecuperacaoSenhaRepository recuperacaoSenhaRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String remetente;

    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    public void solicitarRecuperacao(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Nenhuma conta encontrada com este e-mail."));

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = com.itb.inf2cm.CursiFy.config.ClockConfig.now().plusHours(1);

        RecuperacaoSenha recuperacao = new RecuperacaoSenha();
        recuperacao.setUsuario(usuario);
        recuperacao.setToken(token);
        recuperacao.setDataExpiracao(expiracao);
        recuperacao.setUsado(false);
        recuperacaoSenhaRepository.save(recuperacao);

        String link = frontendUrl + "/redefinir-senha?token=" + token;

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(remetente);
        mensagem.setTo(usuario.getEmail());
        mensagem.setSubject("CursiFy - Recuperação de Senha");
        mensagem.setText("Olá, " + usuario.getNome() + "!\n\n"
                + "Recebemos uma solicitação para redefinir a senha da sua conta.\n\n"
                + "Clique no link abaixo para criar uma nova senha (válido por 1 hora):\n"
                + link + "\n\n"
                + "Se você não solicitou a recuperação, ignore este e-mail.\n\n"
                + "Equipe CursiFy");
        mailSender.send(mensagem);
    }

    public void validarToken(String token) {
        RecuperacaoSenha recuperacao = recuperacaoSenhaRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido."));

        if (recuperacao.getUsado()) {
            throw new IllegalArgumentException("Este link de recuperação já foi utilizado.");
        }
        if (com.itb.inf2cm.CursiFy.config.ClockConfig.now().isAfter(recuperacao.getDataExpiracao())) {
            throw new IllegalArgumentException("Este link de recuperação expirou.");
        }
    }

    public void redefinirSenha(String token, String novaSenha) {
        RecuperacaoSenha recuperacao = recuperacaoSenhaRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido."));

        if (recuperacao.getUsado()) {
            throw new IllegalArgumentException("Este link de recuperação já foi utilizado.");
        }
        if (com.itb.inf2cm.CursiFy.config.ClockConfig.now().isAfter(recuperacao.getDataExpiracao())) {
            throw new IllegalArgumentException("Este link de recuperação expirou.");
        }

        Usuario usuario = recuperacao.getUsuario();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        recuperacao.setUsado(true);
        recuperacaoSenhaRepository.save(recuperacao);
    }

    public void enviarCodigoPerfil(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        String codigo = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        RecuperacaoSenha registro = new RecuperacaoSenha();
        registro.setUsuario(usuario);
        registro.setToken(codigo);
        registro.setDataExpiracao(com.itb.inf2cm.CursiFy.config.ClockConfig.now().plusMinutes(10));
        registro.setUsado(false);
        recuperacaoSenhaRepository.save(registro);
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(remetente);
        mensagem.setTo(usuario.getEmail());
        mensagem.setSubject("CursiFy - Confirmação de perfil");
        mensagem.setText("Seu código para confirmar alterações no perfil é: " + codigo + "\n\nValidade: 10 minutos.");
        mailSender.send(mensagem);
    }

    public void validarCodigoPerfil(Long usuarioId, String codigo) {
        RecuperacaoSenha registro = recuperacaoSenhaRepository.findByToken(codigo)
                .orElseThrow(() -> new IllegalArgumentException("Código inválido ou expirado."));
        if (!registro.getUsuario().getId().equals(usuarioId) || registro.getUsado()
                || com.itb.inf2cm.CursiFy.config.ClockConfig.now().isAfter(registro.getDataExpiracao())) {
            throw new IllegalArgumentException("Código inválido ou expirado.");
        }
        registro.setUsado(true);
        recuperacaoSenhaRepository.save(registro);
    }
}
