package com.algaworks.algafood_api.domain.exception;


import java.io.Serial;

public class EntidadeEmUsoException extends NegocioException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeEmUsoException(String message) {
        super(message);
    }
    public EntidadeEmUsoException(Long id) {
        super(String.format(
                "Entidade de id '%s' está em uso, logo não pode ser removida!" , id
        ));
    }

}
