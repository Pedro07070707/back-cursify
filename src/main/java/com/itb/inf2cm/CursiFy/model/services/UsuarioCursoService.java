package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.UsuarioCurso;
import com.itb.inf2cm.CursiFy.model.repository.UsuarioCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioCursoService {

    @Autowired
    private UsuarioCursoRepository usuarioCursoRepository;

    public List<UsuarioCurso> findAll() {
        return usuarioCursoRepository.findAll();
    }

    /*public UsuarioCurso save(UsuarioCurso usuarioCurso) {
        usuarioCurso.setStatusUsuarioCurso("Ativo");
        return usuarioCursoRepository.save(usuarioCurso);
    }*/

    public UsuarioCurso findById(Long id) {
        return usuarioCursoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("UsuarioCurso não encontrado como o Id" + id));
    }

    public UsuarioCurso update(Long id, UsuarioCurso usuarioCurso) {
        UsuarioCurso usuarioCursoExistente = findById(id);
        return usuarioCursoRepository.save(usuarioCursoExistente);
    }

    public void delete(Long id) {
        UsuarioCurso usuarioCursoExistente = findById(id);
        usuarioCursoRepository.delete(usuarioCursoExistente);
    }
}
