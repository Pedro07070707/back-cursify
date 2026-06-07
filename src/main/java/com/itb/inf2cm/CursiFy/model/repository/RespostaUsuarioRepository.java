package com.itb.inf2cm.CursiFy.model.repository;

import com.itb.inf2cm.CursiFy.model.entity.RespostaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaUsuarioRepository extends JpaRepository<RespostaUsuario, Long> {
    List<RespostaUsuario> findByUsuarioIdAndQuestaoIdOrderByTentativaNumeroDesc(Long usuarioId, Long questaoId);
    Integer countByUsuarioIdAndQuestaoId(Long usuarioId, Long questaoId);
}
