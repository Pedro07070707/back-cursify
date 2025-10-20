package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
