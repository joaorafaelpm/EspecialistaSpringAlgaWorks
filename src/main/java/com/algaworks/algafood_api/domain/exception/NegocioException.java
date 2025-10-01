package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NegocioException extends RuntimeException {
    public NegocioException(String message) {
        super(message);
    }
    public NegocioException(String message , Throwable cause) {
        super(message , cause);
    }
}
