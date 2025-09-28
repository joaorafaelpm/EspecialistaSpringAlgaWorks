package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(value = HttpStatus.NOT_FOUND , reason = "Entidade não encontrada")
public class EntidadeNaoEncontradaException extends ResponseStatusException {
    public EntidadeNaoEncontradaException(String message) {
        super(HttpStatus.NOT_FOUND , message);
    }
    public EntidadeNaoEncontradaException(HttpStatus status ,String message) {
        super(status , message);
    }
}
