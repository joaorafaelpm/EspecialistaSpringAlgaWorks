package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntidadeEmUsoException extends NegocioException {

    public EntidadeEmUsoException(String message) {
        super(message);
    }
    public EntidadeEmUsoException(String message , Long id) {
        super(message);
    }

}
