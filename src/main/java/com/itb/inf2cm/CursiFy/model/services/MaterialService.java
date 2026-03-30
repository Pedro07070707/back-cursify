package com.itb.inf2cm.CursiFy.model.services;

import com.itb.inf2cm.CursiFy.model.entity.Material;
import com.itb.inf2cm.CursiFy.model.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    public Material save(Material material) {
        material.setStatusMaterial("Ativo");
        return materialRepository.save(material);
    }

    public Material findById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Material não encontrado como o Id" + id));
    }

    public Material update(Long id, Material material) {
        Material materialExistente = findById(id);
        materialExistente.setTitulo(material.getTitulo());
        materialExistente.setSubtitulo(material.getSubtitulo());
        materialExistente.setConteudo(material.getConteudo());
        materialExistente.setLink(material.getLink());
        materialExistente.setStatusMaterial(material.getStatusMaterial());
        return materialRepository.save(materialExistente);
    }

    public void delete(Long id) {
        Material materialExistente = findById(id);
        materialRepository.delete(materialExistente);
    }
}
