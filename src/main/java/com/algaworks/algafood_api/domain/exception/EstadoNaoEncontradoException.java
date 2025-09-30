package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public EstadoNaoEncontradoException(String message) {
        super(message);
    }
    public EstadoNaoEncontradoException(Long id) {
        super(String.format("Estado de id %d não encontrado!" , id));
    }
}
