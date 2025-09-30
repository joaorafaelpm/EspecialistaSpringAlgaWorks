package com.algaworks.algafood_api.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }
    public CozinhaNaoEncontradaException(Long id) {
      super(String.format("Cozinha de id %d não encontrado!" , id));
    }
}
