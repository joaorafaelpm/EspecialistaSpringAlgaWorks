package com.algaworks.algafood_api.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {
    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }
    public RestauranteNaoEncontradoException(Long id) {
        super(String.format("Restaurante de id %d não encontrado!" , id));
    }
}
