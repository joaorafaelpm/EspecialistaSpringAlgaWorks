package com.algaworks.algafood_api.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {
    public FormaPagamentoNaoEncontradaException(String message) {
        super(message);
    }
    public FormaPagamentoNaoEncontradaException(Long id) {
        super(String.format("Forma de pagamento de id %d não encontrada!" , id));
    }
}
