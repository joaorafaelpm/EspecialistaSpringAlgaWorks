package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntidadeEmUsoException extends ResponseStatusException {

    public EntidadeEmUsoException(String message) {
        super(HttpStatus.CONFLICT , message);
    }

    public EntidadeEmUsoException(HttpStatus status ,String message) {
        super(status , message);
    }
}
