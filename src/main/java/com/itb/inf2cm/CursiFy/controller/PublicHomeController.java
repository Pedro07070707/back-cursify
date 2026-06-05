package com.itb.inf2cm.CursiFy.controller;

import com.itb.inf2cm.CursiFy.model.dto.FeaturedCourseResponse;
import com.itb.inf2cm.CursiFy.model.dto.PublicHomeResponse;
import com.itb.inf2cm.CursiFy.model.entity.Curso;
import com.itb.inf2cm.CursiFy.model.entity.Usuario;
import com.itb.inf2cm.CursiFy.model.services.CursoService;
import com.itb.inf2cm.CursiFy.model.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public")
public class PublicHomeController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/home")
    public PublicHomeResponse home() {
        List<Curso> cursos = cursoService.findAll();
        List<Usuario> usuarios = usuarioService.findAll();

        long professorCount = usuarios.stream()
                .map(usuario -> usuario.getNivelAcesso() != null ? usuario.getNivelAcesso() : "")
                .filter("PROFESSOR"::equalsIgnoreCase)
                .count();

        List<FeaturedCourseResponse> featuredCourses = cursos.stream()
                .limit(3)
                .map(curso -> new FeaturedCourseResponse(
                        curso.getId(),
                        curso.getNome(),
                        curso.getDescricao(),
                        curso.getCategoria(),
                        curso.getCargaHoraria(),
                        curso.getStatusCurso()
                ))
                .collect(Collectors.toList());

        return new PublicHomeResponse(
                cursos.size(),
                usuarios.size(),
                professorCount,
                featuredCourses
        );
    }
}
