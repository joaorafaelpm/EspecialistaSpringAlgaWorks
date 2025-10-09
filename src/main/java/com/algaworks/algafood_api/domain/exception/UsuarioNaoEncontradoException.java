package com.algaworks.algafood_api.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
    public UsuarioNaoEncontradoException(Long id) {
        super(String.format("Usuário de id %d não encontrado!" , id));
    }
}
