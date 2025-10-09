package com.algaworks.algafood_api.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public GrupoNaoEncontradoException(String message) {
        super(message);
    }
    public GrupoNaoEncontradoException(Long id) {
        super(String.format("Grupo de id %d não encontrado!" , id));
    }
}
