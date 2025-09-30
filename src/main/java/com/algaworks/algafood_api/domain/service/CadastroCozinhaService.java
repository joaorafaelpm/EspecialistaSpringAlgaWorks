package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EntidadeInvalida;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CadastroCozinhaService {

    CozinhaRepository cozinhaRepository;

    public Cozinha save (Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public Cozinha findById (Long id) {
        return cozinhaRepository.findById(id).orElseThrow(() ->
                new CozinhaNaoEncontradaException(id));
    }

    public void remove (Long id) {
        try {
            Cozinha cozinha = findById(id);
            cozinhaRepository.delete(cozinha);
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    HttpStatus.BAD_REQUEST ,  String.format("Cozinha de código %d está em uso, logo, não pode ser removida!" , id)
            );
        }
    }

}
