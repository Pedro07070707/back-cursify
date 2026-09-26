package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Chat;
import com.itb.inf2cm.CursiFy.model.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.itb.inf2cm.CursiFy.model.repository.UsuarioCursoRepository;
import com.itb.inf2cm.CursiFy.model.repository.CursoRepository;
import com.itb.inf2cm.CursiFy.model.repository.UsuarioRepository;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UsuarioCursoRepository usuarioCursoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    public Chat save(Chat chat) {
        if (chat.getMensagem() == null || chat.getRemetenteId() == null || chat.getDestinatarioId() == null) {
            throw new IllegalArgumentException("Mensagem, remetenteId e destinatarioId são obrigatórios");
        }
        String remetenteNivel = usuarioRepository.findById(chat.getRemetenteId())
                .map(u -> u.getNivelAcesso() == null ? "" : u.getNivelAcesso().trim().toUpperCase())
                .orElse("");
        String destinatarioNivel = usuarioRepository.findById(chat.getDestinatarioId())
                .map(u -> u.getNivelAcesso() == null ? "" : u.getNivelAcesso().trim().toUpperCase())
                .orElse("");
        boolean isProfessor = "PROFESSOR".equals(remetenteNivel) || "TEACHER".equals(remetenteNivel);
        boolean isAluno = "ALUNO".equals(remetenteNivel) || "STUDENT".equals(remetenteNivel);
        boolean isProfessorDest = "PROFESSOR".equals(destinatarioNivel) || "TEACHER".equals(destinatarioNivel);
        boolean isAlunoDest = "ALUNO".equals(destinatarioNivel) || "STUDENT".equals(destinatarioNivel);
        boolean professorAluno = (isProfessor && isAlunoDest) || (isAluno && isProfessorDest);
        if (!professorAluno) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.FORBIDDEN,
                    "O chat funciona somente entre professor e aluno"
            );
        }
        Object[] shared = usuarioCursoRepository.findSharedCourse(chat.getRemetenteId(), chat.getDestinatarioId());
        if (shared == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.FORBIDDEN,
                    "Professor e aluno precisam compartilhar uma matrícula ativa"
            );
        }
        Object[] row = (shared.length > 0 && shared[0] instanceof Object[]) ? (Object[]) shared[0] : shared;
        chat.setCursoId(((Number) row[0]).longValue());
        chat.setCursoNome(String.valueOf(row[1]));
        chat.setUsuarioId(chat.getRemetenteId());
        chat.getMensagem().setRemetenteId(chat.getRemetenteId());
        chat.getMensagem().setDestinatarioId(chat.getDestinatarioId());
        chat.setStatusChat("Ativo");
        chat.setDataChat(com.itb.inf2cm.CursiFy.config.ClockConfig.now());
        chat.getMensagem().setDataMensagem(com.itb.inf2cm.CursiFy.config.ClockConfig.now());
        return chatRepository.save(chat);
    }

    public List<Chat> findConversation(Long userA, Long userB) {
        List<Chat> result = chatRepository.findConversation(userA, userB);
        result.forEach(c -> { if (c.getCursoId() != null) cursoRepository.findById(c.getCursoId()).ifPresent(course -> c.setCursoNome(course.getNome())); });
        return result;
    }

    public Chat findById(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Chat não encontrado como o Id" + id));
    }

    public Chat update(Long id, Chat chat) {
        Chat chatExistente = findById(id);
        chatExistente.setRemetente(chat.getRemetente());
        chatExistente.setDataChat(chat.getDataChat());
        chatExistente.setStatusChat(chat.getStatusChat());
        return chatRepository.save(chatExistente);
    }

    public void delete(Long id) {
        Chat chatExistente = findById(id);
        chatRepository.delete(chatExistente);
    }
}
