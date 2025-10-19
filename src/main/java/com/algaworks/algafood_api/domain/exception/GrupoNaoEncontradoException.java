package com.algaworks.algafood_api.domain.exception;

import java.io.Serial;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public GrupoNaoEncontradoException(String message) {
        super(message);
    }
    public GrupoNaoEncontradoException(Long id) {
        super(String.format("Grupo de id %d não encontrado!" , id));
    }
}
