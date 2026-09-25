package com.itb.inf2cm.CursiFy.controller;
import com.itb.inf2cm.CursiFy.model.entity.PreferenciaCurso;
import com.itb.inf2cm.CursiFy.model.repository.PreferenciaCursoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/preferencia")
public class PreferenciaCursoController {
 private final PreferenciaCursoRepository repository;
 public PreferenciaCursoController(PreferenciaCursoRepository repository){this.repository=repository;}
 @GetMapping public List<PreferenciaCurso> list(@RequestParam Long usuarioId,@RequestParam String tipo){return repository.findByUsuarioIdAndTipo(usuarioId,tipo);}
 @PutMapping public PreferenciaCurso save(@RequestBody PreferenciaCurso input){
  return repository.findByUsuarioIdAndCursoIdAndTipo(input.getUsuarioId(),input.getCursoId(),input.getTipo()).map(old->{old.setValor(input.getValor());return repository.save(old);}).orElseGet(()->repository.save(input));
 }
 @DeleteMapping public void delete(@RequestParam Long usuarioId,@RequestParam Long cursoId,@RequestParam String tipo){repository.findByUsuarioIdAndCursoIdAndTipo(usuarioId,cursoId,tipo).ifPresent(repository::delete);}
}
