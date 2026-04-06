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
        if (material.getStatusMaterial() == null || material.getStatusMaterial().isBlank()) {
            material.setStatusMaterial("Nao concluido");
        }
        return materialRepository.save(material);
    }

    public Material findById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material nao encontrado com o id " + id));
    }

    public Material update(Long id, Material material) {
        Material materialExistente = findById(id);
        materialExistente.setTitulo(material.getTitulo());
        materialExistente.setSubtitulo(material.getSubtitulo());
        materialExistente.setConteudo(material.getConteudo());
        materialExistente.setLink(material.getLink());
        materialExistente.setStatusMaterial(material.getStatusMaterial());
        if (material.getUsuario() != null) {
            materialExistente.setUsuario(material.getUsuario());
        }
        if (material.getCurso() != null) {
            materialExistente.setCurso(material.getCurso());
        }
        return materialRepository.save(materialExistente);
    }

    public void delete(Long id) {
        Material materialExistente = findById(id);
        materialRepository.delete(materialExistente);
    }
}
