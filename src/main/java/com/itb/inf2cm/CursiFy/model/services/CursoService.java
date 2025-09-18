package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Curso;
import com.itb.inf2cm.CursiFy.model.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    public Curso save(Curso curso) {
        curso.setCodStatus(true);
        return cursoRepository.save(curso);
    }

    public Curso findById(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Curso não encontrado como o Id" + id));
    }

    public Curso update(Long id, Curso curso) {
        Curso produtoExistente = findById(id);
        cursoExistente.setNome(curso.getNome());
        cursoExistente.setDescricao(curso.getDescricao());
        curso.setTipo(curso.getTipo());
        cursoExistente.setQuantidadeEstoque(curso.getQuantidadeEstoque());
        cursoExistente.setValorCompra(curso.getValorCompra());
        cursoExistente.setValorVenda(curso.getValorVenda());
        return cursoRepository.save(cursoExistente);
    }

    public void delete(Long id) {
        Curso cursoExistente = findById(id);
        cursoRepository.delete(cursoExistente);
    }
}
