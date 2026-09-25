package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE "
            + "(c.remetenteId = :userA AND c.destinatarioId = :userB) "
            + "OR (c.remetenteId = :userB AND c.destinatarioId = :userA) "
            + "ORDER BY c.dataChat ASC")
    List<Chat> findConversation(@Param("userA") Long userA, @Param("userB") Long userB);
}
