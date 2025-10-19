package com.algaworks.algafood_api.domain.exception;

import java.io.Serial;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PermissaoNaoEncontradaException(String message) {
        super(message);
    }
    public PermissaoNaoEncontradaException(Long id) {
        super(String.format("Permissao de id %d não encontrado!" , id));
    }
}