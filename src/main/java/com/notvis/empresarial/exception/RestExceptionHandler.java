package com.notvis.empresarial.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

public class RestExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex, HttpServletRequest req) {
        var body = new ApiError(Instant.now(), HttpStatus.NOT_FOUND.value(),
                "Not Found", ex.getMessage(), req.getRequestURI(), List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArg(IllegalArgumentException ex, HttpServletRequest req) {
        var body = new ApiError(Instant.now(), HttpStatus.BAD_REQUEST.value(),
                "Bad Request", ex.getMessage(), req.getRequestURI(), List.of());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        var fields = ex.getBindingResult().getFieldErrors()
                .stream().map(fe -> fe.getField() + ": " + fe.getDefaultMessage()).toList();
        var body = new ApiError(Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation Error", "Erro de validação dos campos.", req.getRequestURI(), fields);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        var fields = ex.getConstraintViolations()
                .stream().map(v -> v.getPropertyPath() + ": " + v.getMessage()).toList();
        var body = new ApiError(Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation Error", "Erro de validação dos parâmetros.", req.getRequestURI(), fields);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }
}
