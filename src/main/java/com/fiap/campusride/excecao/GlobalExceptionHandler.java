package com.fiap.campusride.excecao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidacao(MethodArgumentNotValidException ex) {
        List<String> detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();

        ErrorResponse erro = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Dados invalidos",
                "Um ou mais campos nao passaram na validacao",
                detalhes
        );
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNaoEncontrado(ResourceNotFoundException ex) {
        ErrorResponse erro = ErrorResponse.of(
                HttpStatus.NOT_FOUND.value(), "Recurso nao encontrado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleRegraDeNegocio(BusinessException ex) {
        ErrorResponse erro = ErrorResponse.of(
                HttpStatus.CONFLICT.value(), "Regra de negocio violada", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenerico(Exception ex) {
        ErrorResponse erro = ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.");
        return ResponseEntity.internalServerError().body(erro);
    }
}