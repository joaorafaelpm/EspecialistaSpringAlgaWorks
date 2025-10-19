package com.algaworks.algafood_api.domain.exception;

import java.io.Serial;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;


    public CidadeNaoEncontradaException(String message) {
        super(message);
    }
    public CidadeNaoEncontradaException(Long id) {
        super(String.format("Cidade de id %d não encontrado!" , id));
    }
}
