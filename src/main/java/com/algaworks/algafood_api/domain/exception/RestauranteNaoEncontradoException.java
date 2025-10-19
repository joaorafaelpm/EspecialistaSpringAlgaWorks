package com.algaworks.algafood_api.domain.exception;

import java.io.Serial;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }
    public RestauranteNaoEncontradoException(Long id) {
        super(String.format("Restaurante de id %d não encontrado!" , id));
    }
}
