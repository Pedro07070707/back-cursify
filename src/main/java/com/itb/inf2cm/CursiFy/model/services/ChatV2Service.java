package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.dto.ChatConversationResponse;
import com.itb.inf2cm.CursiFy.model.dto.ChatMessageResponse;
import com.itb.inf2cm.CursiFy.model.entity.Conversa;
import com.itb.inf2cm.CursiFy.model.entity.MensagemChat;
import com.itb.inf2cm.CursiFy.model.repository.ConversaRepository;
import com.itb.inf2cm.CursiFy.model.repository.MensagemChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatV2Service {
    @Autowired private ConversaRepository conversaRepository;
    @Autowired private MensagemChatRepository mensagemChatRepository;

    public List<ChatConversationResponse> findConversations() {
        return conversaRepository.findAll().stream().map(c -> new ChatConversationResponse(c.getId(), c.getTipo(), c.getTrilhaId(), c.getNome())).toList();
    }

    public List<ChatMessageResponse> findMessages(Long conversaId) {
        return mensagemChatRepository.findByConversaIdOrderByEnviadoEmAsc(conversaId).stream()
                .map(m -> new ChatMessageResponse(m.getId(), m.getConversaId(), m.getRemetenteId(), m.getConteudo(), m.getTipo(), m.getEnviadoEm()))
                .toList();
    }

    public ChatMessageResponse sendMessage(Long conversaId, Long remetenteId, String conteudo, String tipo) {
        MensagemChat m = new MensagemChat();
        m.setConversaId(conversaId);
        m.setRemetenteId(remetenteId);
        m.setConteudo(conteudo);
        m.setTipo(tipo == null ? "TEXTO" : tipo);
        m.setEnviadoEm(LocalDateTime.now());
        m.setDeletado(false);
        m = mensagemChatRepository.save(m);
        return new ChatMessageResponse(m.getId(), m.getConversaId(), m.getRemetenteId(), m.getConteudo(), m.getTipo(), m.getEnviadoEm());
    }
}
