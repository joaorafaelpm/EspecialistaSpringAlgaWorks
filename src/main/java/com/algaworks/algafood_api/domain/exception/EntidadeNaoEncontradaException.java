package com.algaworks.algafood_api.domain.exception;

import java.io.Serial;

public class EntidadeNaoEncontradaException extends NegocioException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }

}
