package com.itb.inf2cm.CursiFy.controller;
import com.itb.inf2cm.CursiFy.model.entity.PreferenciaCurso;
import com.itb.inf2cm.CursiFy.model.repository.PreferenciaCursoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController @RequestMapping("/api/v1/preferencia")
public class PreferenciaCursoController {
 private final PreferenciaCursoRepository repository;
 public PreferenciaCursoController(PreferenciaCursoRepository repository){this.repository=repository;}
 @GetMapping public List<PreferenciaCurso> list(@RequestParam Long usuarioId,@RequestParam String tipo){return repository.findByUsuarioIdAndTipo(usuarioId,tipo);}
 @GetMapping("/curso") public Map<String,Object> courseRating(@RequestParam Long cursoId,@RequestParam(required=false) Long usuarioId){
  List<PreferenciaCurso> ratings = repository.findByCursoIdAndTipo(cursoId, "AVALIACAO");
  double average = ratings.stream().mapToDouble(p -> Double.parseDouble(p.getValor())).average().orElse(0d);
  Integer userRating = usuarioId == null ? null : ratings.stream().filter(p -> usuarioId.equals(p.getUsuarioId())).map(PreferenciaCurso::getValor).findFirst().map(value -> (int) Double.parseDouble(value)).orElse(null);
  return Map.of("average", Math.round(average * 10d) / 10d, "count", ratings.size(), "userRating", userRating == null ? 0 : userRating);
 }
 @PutMapping public PreferenciaCurso save(@RequestBody PreferenciaCurso input){
  return repository.findByUsuarioIdAndCursoIdAndTipo(input.getUsuarioId(),input.getCursoId(),input.getTipo()).map(old->{old.setValor(input.getValor());return repository.save(old);}).orElseGet(()->repository.save(input));
 }
 @DeleteMapping public void delete(@RequestParam Long usuarioId,@RequestParam Long cursoId,@RequestParam String tipo){repository.findByUsuarioIdAndCursoIdAndTipo(usuarioId,cursoId,tipo).ifPresent(repository::delete);}
}
