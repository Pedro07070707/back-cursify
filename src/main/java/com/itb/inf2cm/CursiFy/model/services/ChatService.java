package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Chat;
import com.itb.inf2cm.CursiFy.model.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    public Chat save(Chat chat) {
        chat.setStatusChat(true);
        return chatRepository.save(chat);
    }

    public Chat findById(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Chat não encontrado como o Id" + id));
    }

    public Chat update(Long id, Chat chat) {
        Chat chatExistente = findById(id);
        chat.setDataChat(chat.getDataChat());
        chat.setMensagem(chat.getMensagem());
        chatExistente.setStatusChat(chat.getStatusChat());
        return chatRepository.save(chatExistente);
    }

    public void delete(Long id) {
        Chat chatExistente = findById(id);
        chatRepository.delete(chatExistente);
    }
}
