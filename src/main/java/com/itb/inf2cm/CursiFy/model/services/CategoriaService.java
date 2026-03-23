package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria save(Categoria categoria) {
        categoria.setStatusCategoria(true);
        return categoriaRepository.save(categoria);
    }

    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Categoria não encontrada como o Id" + id));
    }

    public Categoria update(Long id, Categoria categoria) {
        Categoria categoriaExistente = findById(id);
        categoriaExistente.setNome(categoria.getNome());
        categoriaExistente.setDescricao(categoria.getDescricao());
        categoriaExistente.setStatusCategoria(categoria.getStatusCategoria());
        return categoriaRepository.save(categoriaExistente);
    }

    public void delete(Long id) {
        Categoria categoriaExistente = findById(id);
        categoriaRepository.delete(categoriaExistente);
    }
}
