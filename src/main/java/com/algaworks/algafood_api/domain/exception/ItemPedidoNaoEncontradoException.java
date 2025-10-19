package com.algaworks.algafood_api.domain.exception;

import java.io.Serial;

public class ItemPedidoNaoEncontradoException extends EntidadeNaoEncontradaException{

    @Serial
    private static final long serialVersionUID = 1L;

    public ItemPedidoNaoEncontradoException(Long id) {
        super(String.format("Item do pedido de id '%d' não encontrado!" , id));
    }
}
