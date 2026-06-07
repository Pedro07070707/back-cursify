package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.MensagemChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemChatRepository extends JpaRepository<MensagemChat, Long> {
    List<MensagemChat> findByConversaIdOrderByEnviadoEmAsc(Long conversaId);
}
