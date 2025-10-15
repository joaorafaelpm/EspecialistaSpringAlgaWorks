package com.algaworks.algafood_api.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public FotoProdutoNaoEncontradoException(String message) {
        super(message);
    }
    public FotoProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
        this(String.format("Foto do produto de id '%d' não encontrado no restaurante de id %d",
                produtoId, restauranteId));
    }
}
