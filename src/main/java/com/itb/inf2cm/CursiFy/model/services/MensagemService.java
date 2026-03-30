package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Mensagem;
import com.itb.inf2cm.CursiFy.model.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    public List<Mensagem> findAll() {
        return mensagemRepository.findAll();
    }

    public Mensagem save(Mensagem mensagem) {
        mensagem.setStatusMensagem("Ativo");
        return mensagemRepository.save(mensagem);
    }

    public Mensagem findById(Long id) {
        return mensagemRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Mensagem não encontrado como o Id" + id));
    }

    public Mensagem update(Long id, Mensagem mensagem) {
        Mensagem mensagemExistente = findById(id);
        mensagemExistente.setConteudo(mensagem.getConteudo());
        mensagemExistente.setDataMensagem(mensagem.getDataMensagem());
        mensagemExistente.setStatusMensagem(mensagem.getStatusMensagem());
        return mensagemRepository.save(mensagemExistente);
    }

    public void delete(Long id) {
        Mensagem mensagemExistente = findById(id);
        mensagemRepository.delete(mensagemExistente);
    }
}
