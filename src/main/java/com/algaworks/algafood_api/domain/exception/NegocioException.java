package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NegocioException extends ResponseStatusException {
    public NegocioException(String message) {
        super(HttpStatus.BAD_REQUEST , message);
    }
    public NegocioException(HttpStatus status ,String message) {
        super(status , message);
    }
}
