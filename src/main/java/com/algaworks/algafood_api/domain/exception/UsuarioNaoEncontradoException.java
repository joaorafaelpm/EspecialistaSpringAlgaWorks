package com.algaworks.algafood_api.domain.exception;

import java.io.Serial;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
    public UsuarioNaoEncontradoException(Long id) {
        super(String.format("Usuário de id %d não encontrado!" , id));
    }
}
