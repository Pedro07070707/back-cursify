package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.dto.ConcluirNoResponse;
import com.itb.inf2cm.CursiFy.model.dto.ProjetoEnviadoRequest;
import com.itb.inf2cm.CursiFy.model.dto.ProjetoEnviadoResponse;
import com.itb.inf2cm.CursiFy.model.dto.ResponderNoItemResponse;
import com.itb.inf2cm.CursiFy.model.dto.ResponderNoRequest;
import com.itb.inf2cm.CursiFy.model.dto.ResponderNoResponse;
import com.itb.inf2cm.CursiFy.model.dto.RespostaItemRequest;
import com.itb.inf2cm.CursiFy.model.entity.Alternativa;
import com.itb.inf2cm.CursiFy.model.entity.NoTrilha;
import com.itb.inf2cm.CursiFy.model.entity.ProgressoNo;
import com.itb.inf2cm.CursiFy.model.entity.Questao;
import com.itb.inf2cm.CursiFy.model.entity.RespostaUsuario;
import com.itb.inf2cm.CursiFy.model.entity.XpUsuario;
import com.itb.inf2cm.CursiFy.model.repository.AlternativaRepository;
import com.itb.inf2cm.CursiFy.model.repository.NoTrilhaRepository;
import com.itb.inf2cm.CursiFy.model.repository.ProgressoNoRepository;
import com.itb.inf2cm.CursiFy.model.repository.QuestaoRepository;
import com.itb.inf2cm.CursiFy.model.repository.RespostaUsuarioRepository;
import com.itb.inf2cm.CursiFy.model.repository.XpUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProgressoService {
    @Autowired private ProgressoNoRepository progressoNoRepository;
    @Autowired private XpUsuarioRepository xpUsuarioRepository;
    @Autowired private NoTrilhaRepository noTrilhaRepository;
    @Autowired private QuestaoRepository questaoRepository;
    @Autowired private AlternativaRepository alternativaRepository;
    @Autowired private RespostaUsuarioRepository respostaUsuarioRepository;
    @Autowired private ProjetoEnviadoService projetoEnviadoService;

    public ConcluirNoResponse concluirNo(Long usuarioId, Long noId) {
        NoTrilha no = noTrilhaRepository.findById(noId).orElseThrow(() -> new RuntimeException("No nao encontrado"));
        ProgressoNo progresso = progressoNoRepository.findByUsuarioIdAndNoId(usuarioId, noId).orElseGet(ProgressoNo::new);
        progresso.setUsuarioId(usuarioId);
        progresso.setNoId(noId);
        progresso.setStatus("CONCLUIDO");
        progresso.setTentativas((progresso.getTentativas() == null ? 0 : progresso.getTentativas()) + 1);
        progresso.setXpGanho(no.getXpRecompensa() == null ? 10 : no.getXpRecompensa());
        progresso.setConcluidoEm(LocalDateTime.now());
        progresso.setAtualizadoEm(LocalDateTime.now());
        progressoNoRepository.save(progresso);

        XpUsuario xp = xpUsuarioRepository.findByUsuarioId(usuarioId).orElseGet(() -> {
            XpUsuario novo = new XpUsuario();
            novo.setUsuarioId(usuarioId);
            novo.setXpTotal(0);
            novo.setNivel(1);
            novo.setStreakAtual(0);
            novo.setStreakMaximo(0);
            return novo;
        });
        xp.setXpTotal((xp.getXpTotal() == null ? 0 : xp.getXpTotal()) + progresso.getXpGanho());
        xp.setNivel(Math.max(1, (xp.getXpTotal() / 200) + 1));
        xpUsuarioRepository.save(xp);

        return new ConcluirNoResponse(noId, usuarioId, progresso.getStatus(), progresso.getXpGanho(), xp.getXpTotal());
    }

    public ResponderNoResponse responderNo(Long noId, ResponderNoRequest request) {
        NoTrilha no = noTrilhaRepository.findById(noId).orElseThrow(() -> new RuntimeException("No nao encontrado"));
        if ("PROJETO".equalsIgnoreCase(no.getTipo())) {
            ProjetoEnviadoRequest projetoRequest = new ProjetoEnviadoRequest();
            projetoRequest.setUsuarioId(request.getUsuarioId());
            projetoRequest.setRespostaTexto(request.getRespostaTexto());
            projetoRequest.setArquivoUrl(request.getArquivoUrl());
            ProjetoEnviadoResponse projeto = projetoEnviadoService.enviar(noId, projetoRequest);
            return new ResponderNoResponse(
                noId,
                request.getUsuarioId(),
                no.getTipo(),
                0,
                0,
                0.0,
                no.getNotaMinima(),
                0,
                xpUsuarioRepository.findByUsuarioId(request.getUsuarioId()).map(XpUsuario::getXpTotal).orElse(0),
                false,
                1,
                "Projeto enviado para avaliacao. Aguarde o retorno do professor.",
                java.util.List.of(new ResponderNoItemResponse(null, null, false, 1, "Projeto enviado com status " + projeto.getStatus()))
            );
        }
        List<Questao> questoes = questaoRepository.findByNoIdOrderByOrdemAsc(noId);
        ProgressoNo progresso = request.getUsuarioId() == null
            ? null
            : progressoNoRepository.findByUsuarioIdAndNoId(request.getUsuarioId(), noId).orElseGet(ProgressoNo::new);
        Map<Long, Long> respostasPorQuestao = new HashMap<>();
        if (request.getRespostas() != null) {
            for (RespostaItemRequest resposta : request.getRespostas()) {
                if (resposta != null && resposta.getQuestaoId() != null) {
                    respostasPorQuestao.put(resposta.getQuestaoId(), resposta.getAlternativaId());
                }
            }
        }
        int total = questoes.size();
        int acertos = 0;
        int tentativasAnteriores = progresso != null && progresso.getTentativas() != null ? progresso.getTentativas() : 0;
        java.util.List<ResponderNoItemResponse> detalhes = new java.util.ArrayList<>();
        if (request.getUsuarioId() != null) {
            for (Questao questao : questoes) {
                int tentativaNumero = respostaUsuarioRepository.countByUsuarioIdAndQuestaoId(request.getUsuarioId(), questao.getId()) + 1;
                tentativasAnteriores = Math.max(tentativasAnteriores, tentativaNumero);
                Long alternativaEscolhidaId = respostasPorQuestao.get(questao.getId());
                boolean correta = false;
                String feedback = "Resposta registrada.";
                if (alternativaEscolhidaId != null) {
                    Optional<Alternativa> alternativa = alternativaRepository.findByIdAndQuestaoId(alternativaEscolhidaId, questao.getId());
                    correta = alternativa.map(Alternativa::getCorreta).orElse(false);
                    feedback = correta
                        ? "Resposta correta."
                        : alternativa.map(value -> "Resposta incorreta. Tente novamente.").orElse("Alternativa invalida para esta questao.");
                } else {
                    feedback = "Nenhuma alternativa selecionada.";
                }
                if (correta) {
                    acertos++;
                }
                RespostaUsuario respostaUsuario = new RespostaUsuario();
                respostaUsuario.setUsuarioId(request.getUsuarioId());
                respostaUsuario.setQuestaoId(questao.getId());
                respostaUsuario.setAlternativaId(alternativaEscolhidaId);
                respostaUsuario.setRespostaTexto(null);
                respostaUsuario.setCorreta(correta);
                respostaUsuario.setTentativaNumero(tentativaNumero);
                respostaUsuario.setRespondidoEm(LocalDateTime.now());
                respostaUsuarioRepository.save(respostaUsuario);
                detalhes.add(new ResponderNoItemResponse(questao.getId(), alternativaEscolhidaId, correta, tentativaNumero, feedback));
            }
        }
        Double nota = total == 0 ? 0.0 : (acertos * 10.0) / total;
        boolean checkpoint = "CHECKPOINT".equalsIgnoreCase(no.getTipo());
        boolean projeto = "PROJETO".equalsIgnoreCase(no.getTipo());
        boolean aprovado = no.getNotaMinima() == null || nota >= no.getNotaMinima();
        int xpGanho = aprovado ? (no.getXpRecompensa() == null ? 10 : no.getXpRecompensa()) : 0;
        int tentativas = tentativasAnteriores + 1;
        boolean bloqueadoPorTentativas = checkpoint && no.getMaxTentativas() != null && tentativas >= no.getMaxTentativas() && !aprovado;

        if (progresso == null && request.getUsuarioId() != null) {
            progresso = new ProgressoNo();
            progresso.setUsuarioId(request.getUsuarioId());
            progresso.setNoId(noId);
        }

        if (progresso != null) {
            progresso.setTentativas(tentativas);
            progresso.setNotaObtida(nota);
            progresso.setXpGanho(xpGanho);
            progresso.setAtualizadoEm(LocalDateTime.now());
            if (aprovado) {
                progresso.setStatus("CONCLUIDO");
                progresso.setConcluidoEm(LocalDateTime.now());
            } else if (bloqueadoPorTentativas) {
                progresso.setStatus("BLOQUEADO");
            } else {
                progresso.setStatus("EM_ANDAMENTO");
            }
            progressoNoRepository.save(progresso);
        }

        if (aprovado) {
            concluirNo(request.getUsuarioId(), noId);
        }
        XpUsuario xp = xpUsuarioRepository.findByUsuarioId(request.getUsuarioId()).orElseGet(() -> {
            XpUsuario novo = new XpUsuario();
            novo.setUsuarioId(request.getUsuarioId());
            novo.setXpTotal(0);
            novo.setNivel(1);
            novo.setStreakAtual(0);
            novo.setStreakMaximo(0);
            return novo;
        });
        if (xpGanho > 0) {
            xp.setXpTotal((xp.getXpTotal() == null ? 0 : xp.getXpTotal()) + xpGanho);
            xp.setNivel(Math.max(1, (xp.getXpTotal() / 200) + 1));
            xpUsuarioRepository.save(xp);
        }
        String mensagem;
        if (checkpoint) {
            if (bloqueadoPorTentativas) {
                mensagem = "Checkpoint bloqueado pelo limite de tentativas. Aguarde reabertura ou revise o conteudo.";
            } else {
                mensagem = aprovado
                ? "Checkpoint aprovado. O proximo nodo foi liberado."
                : "Checkpoint nao atingiu a nota minima. Revise e tente novamente.";
            }
        } else {
            mensagem = aprovado
                ? "Nodo concluido com sucesso."
                : "Resultado registrado. Revise as respostas e tente novamente.";
        }
        Integer tentativasResponse = tentativas > 0 ? tentativas : null;
        return new ResponderNoResponse(
            noId,
            request.getUsuarioId(),
            no.getTipo(),
            acertos,
            total,
            nota,
            no.getNotaMinima(),
            xpGanho,
            xp.getXpTotal(),
            aprovado,
            tentativasResponse,
            mensagem,
            detalhes
        );
    }
}
