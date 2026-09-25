package com.itb.inf2cm.CursiFy.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Map<String, Object>> handleNumberFormat(NumberFormatException e) {
        return ResponseEntity.badRequest().body(Map.of(
                "status", 400,
                "error", "Bad Request",
                "message", "O id informado não é válido: " + e.getMessage()
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, Object>> handleDatabase(DataAccessException e) {
        return ResponseEntity.status(500).body(Map.of(
                "status", 500,
                "error", "Database Error",
                "message", e.getMostSpecificCause() != null
                        ? e.getMostSpecificCause().getMessage()
                        : e.getMessage()
        ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleIntegrity(DataIntegrityViolationException e) {
        String detail = e.getMostSpecificCause() == null ? "" : e.getMostSpecificCause().getMessage();
        String message = detail != null && detail.toLowerCase().contains("cpf")
                ? "Este CPF já está cadastrado."
                : detail != null && detail.toLowerCase().contains("email")
                ? "Este e-mail já está cadastrado."
                : "Não foi possível salvar: já existe um registro com esses dados.";
        return ResponseEntity.status(409).body(Map.of("status", 409, "error", "Conflict", "message", message));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException e) {
        return ResponseEntity.status(500).body(Map.of(
                "status", 500,
                "error", "Internal Server Error",
                "message", e.getMessage()
        ));
    }
}
