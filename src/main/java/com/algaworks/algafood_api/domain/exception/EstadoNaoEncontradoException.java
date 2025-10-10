package com.algaworks.algafood_api.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public EstadoNaoEncontradoException(String message) {
        super(message);
    }
    public EstadoNaoEncontradoException(Long id) {
        super(String.format("Estado de id %d não encontrado!" , id));
    }
}
