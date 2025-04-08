package br.com.olua.handler;

import br.com.olua.exceptions.ApplicationException;
import br.com.olua.exceptions.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<List<String>> handleApplicationException(ApplicationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrors());
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<String> handleDomainException(DomainException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}