package com.algaworks.algafood_api.domain.exception;

import java.io.Serial;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FormaPagamentoNaoEncontradaException(String message) {
        super(message);
    }
    public FormaPagamentoNaoEncontradaException(Long id) {
        super(String.format("Forma de pagamento de id %d não encontrada!" , id));
    }
}
