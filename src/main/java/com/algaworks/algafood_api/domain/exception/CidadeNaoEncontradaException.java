package com.algaworks.algafood_api.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
    public CidadeNaoEncontradaException(String message) {
        super(message);
    }
    public CidadeNaoEncontradaException(Long id) {
        super(String.format("Cidade de id %d não encontrado!" , id));
    }
}
