package tn.esprit.autolock.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tn.esprit.autolock.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

// Traduit les exceptions métier en réponses HTTP lisibles (le contrôleur ne produit pas les 404/400 lui-même)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return reponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        return reponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private static ResponseEntity<Map<String, Object>> reponse(HttpStatus statut, String message) {
        Map<String, Object> corps = new LinkedHashMap<>();
        corps.put("statut", statut.value());
        corps.put("message", message);
        corps.put("horodatage", LocalDateTime.now().toString());
        return ResponseEntity.status(statut).body(corps);
    }
}
