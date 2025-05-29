package com.medline.application.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura especificamente os erros de validação (@Valid).
     * 
     * @param ex A exceção lançada pelo Spring.
     * @return Um mapa (que será convertido em JSON) com os campos e seus erros.
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Garante que o Status seja 400
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        // Pega todos os erros de validação
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            // pega o nome do campo
            String fieldName = ((FieldError) error).getField();

            // pega a mensagem de erro padrão
            String errorMessage = error.getDefaultMessage();

            // Adiciona ao mapa
            errors.put(fieldName, errorMessage);

        });
        // retorna o mapa de erros com status 400
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
