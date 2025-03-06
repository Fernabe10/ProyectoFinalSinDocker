package com.fgr.apirest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {
 @ExceptionHandler(RecursoNoEncontradoException.class) //metodo que maneja excepciones de tipo RecursoNoEntontrado
    public ResponseEntity<Map<String, Object>> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        Map<String, Object> error = new HashMap<>(); //map de errores para construir la respuesta
        error.put("timestamp", LocalDateTime.now()); //le pongo la fecha actual
        error.put("status", HttpStatus.NOT_FOUND.value()); //el codigo de error (404)
        error.put("error", "Recurso no encontrado"); //un mensaje breve del error
        error.put("message", ex.getMessage()); //el mensaje personalizado que va en el servicio

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
