package io.github.mqdev.apicursos.exceptions;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleInvalidEnumValue(HttpMessageNotReadableException ex) {
        // Verifica se a exceção foi causada por um valor de enum inválido
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            List<JsonMappingException.Reference> path = ((InvalidFormatException) cause).getPath();
            if (!path.isEmpty()) {
                var fieldName = path.get(0).getFieldName();
                if (fieldName.equals("category")) {
                    return ResponseEntity.badRequest().body(
                            "Categoria inválida. As categorias válidas são: BUSINESS, PROGRAMMING, MANAGEMENT, DESIGN, MARKETING.");
                } else if (fieldName.equals("active")) {
                    return ResponseEntity.badRequest().body(
                            "Valor inválido para o campo 'active'. O valor deve ser 'ACTIVE' ou 'INACTIVE'.");
                } else {
                    return ResponseEntity.badRequest().body("Valor inválido para o campo '" + fieldName + "'.");
                }
            }
        }

        // Se a exceção foi causada por outra coisa, apenas re-lança a exceção
        throw ex;
    }
}
