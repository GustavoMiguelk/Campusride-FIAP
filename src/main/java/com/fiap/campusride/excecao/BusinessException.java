package com.fiap.campusride.excecao;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}