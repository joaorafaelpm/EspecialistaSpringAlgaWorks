package com.algaworks.algafood_api.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public PedidoNaoEncontradoException(String codigo) {
        super(String.format("Pedido de codigo '%d' não encontrado!" , codigo));
    }
    public PedidoNaoEncontradoException(Long id) {
        super(String.format("Pedido de id '%d' não encontrado!" , id));
    }
}
