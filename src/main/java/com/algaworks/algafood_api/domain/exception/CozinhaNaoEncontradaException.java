package com.algaworks.algafood_api.domain.exception;

import java.io.Serial;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }
    public CozinhaNaoEncontradaException(Long id) {
      super(String.format("Cozinha de id %d não encontrado!" , id));
    }
}
