package com.diogonogueira.junit.resource.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> handleNotFound(RuntimeException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class) // input inválido
    public ResponseEntity<Void> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build(); // 400
    }
}
