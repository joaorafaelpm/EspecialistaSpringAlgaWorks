package com.algaworks.algafood_api.domain.exception;

import java.io.Serial;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
    public ProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
        this(String.format("Não existe um cadastro de produto com código '%d' para o restaurante de código '%d'",
                produtoId, restauranteId));
    }
}
