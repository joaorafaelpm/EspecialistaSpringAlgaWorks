package com.algaworks.algafood_api.domain.exception;

import java.io.Serial;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FotoProdutoNaoEncontradoException(String message) {
        super(message);
    }
    public FotoProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
        this(String.format("Foto do produto de id '%d' não encontrado no restaurante de id %d",
                produtoId, restauranteId));
    }
}
