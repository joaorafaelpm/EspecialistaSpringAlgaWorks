package com.algaworks.algafood_api.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {
    public PermissaoNaoEncontradaException(String message) {
        super(message);
    }
    public PermissaoNaoEncontradaException(Long id) {
        super(String.format("Permissao de id %d não encontrado!" , id));
    }
}