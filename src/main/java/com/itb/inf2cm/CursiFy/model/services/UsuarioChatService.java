package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.UsuarioChat;
import com.itb.inf2cm.CursiFy.model.repository.UsuarioChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioChatService {

    @Autowired
    private UsuarioChatRepository usuarioChatRepository;

    public List<UsuarioChat> findAll() {
        return usuarioChatRepository.findAll();
    }

    /*public UsuarioChat save(UsuarioChat usuarioChat) {
        usuarioChat.setStatusUsuarioChat("Ativo");
        return usuarioChatRepository.save(usuarioChat);
    }*/

    public UsuarioChat findById(Long id) {
        return usuarioChatRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("UsuarioChat não encontrado como o Id" + id));
    }

    public UsuarioChat update(Long id, UsuarioChat usuarioChat) {
        UsuarioChat usuarioChatExistente = findById(id);
        return usuarioChatRepository.save(usuarioChatExistente);
    }

    public void delete(Long id) {
        UsuarioChat usuarioChatExistente = findById(id);
        usuarioChatRepository.delete(usuarioChatExistente);
    }
}
