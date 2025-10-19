package com.algaworks.algafood_api.domain.exception;

import java.io.Serial;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradoException(String message) {
        super(message);
    }
    public EstadoNaoEncontradoException(Long id) {
        super(String.format("Estado de id %d não encontrado!" , id));
    }
}
