package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntidadeNaoEncontradaException extends NegocioException {
    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }

}
