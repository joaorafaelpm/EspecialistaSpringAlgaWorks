package com.algaworks.algafood_api.domain.exception;

import java.io.Serial;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradoException(String codigo) {
        super(String.format("Pedido de codigo '%s' não encontrado!" , codigo));
    }
    public PedidoNaoEncontradoException(Long id) {
        super(String.format("Pedido de id '%s' não encontrado!" , id));
    }
}
