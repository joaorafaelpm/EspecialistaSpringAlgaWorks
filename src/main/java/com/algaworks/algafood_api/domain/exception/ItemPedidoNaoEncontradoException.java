package com.algaworks.algafood_api.domain.exception;

public class ItemPedidoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public ItemPedidoNaoEncontradoException(Long id) {
        super(String.format("Item do pedido de id '%d' não encontrado!" , id));
    }
}
