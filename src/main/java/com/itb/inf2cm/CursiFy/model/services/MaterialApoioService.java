package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.MaterialApoio;
import com.itb.inf2cm.CursiFy.model.repository.MaterialApoioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialApoioService {

    @Autowired
    private MaterialApoioRepository materialApoioRepository;

    public List<MaterialApoio> findAll() {
        return materialApoioRepository.findAll();
    }

    public MaterialApoio save(MaterialApoio materialApoio) {
        materialApoio.setStatusMaterialApoio(true);
        return materialApoioRepository.save(materialApoio);
    }

    public MaterialApoio findById(Long id) {
        return materialApoioRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Material de apoio não encontrado como o Id" + id));
    }

    public MaterialApoio update(Long id, MaterialApoio materialApoio) {
        MaterialApoio materialApoioExistente = findById(id);
        materialApoioExistente.setTipoMaterial(materialApoio.getTipoMaterial());
        materialApoioExistente.setTitulo(materialApoio.getTitulo());
        materialApoioExistente.setUrl(materialApoio.getUrl());
        materialApoioExistente.setStatusMaterialApoio(materialApoio.getStatusMaterialApoio());
        return materialApoioRepository.save(materialApoioExistente);
    }

    public void delete(Long id) {
        MaterialApoio materialApoioExistente = findById(id);
        materialApoioRepository.delete(materialApoioExistente);
    }
}
