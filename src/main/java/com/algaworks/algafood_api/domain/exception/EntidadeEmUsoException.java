package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntidadeEmUsoException extends NegocioException {

    public EntidadeEmUsoException(String message) {
        super(message);
    }
    public EntidadeEmUsoException(Long id) {
        super(String.format(
                "Entidade de id '%s' está em uso, logo não pode ser removida!" , id
        ));
    }

}
