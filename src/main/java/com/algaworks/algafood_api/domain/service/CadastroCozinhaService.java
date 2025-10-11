package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CadastroCozinhaService {

    CozinhaRepository cozinhaRepository;

    public List<Cozinha> findAll() {
        return cozinhaRepository.findAll();
    }

    public Cozinha findById (Long id) {
        return cozinhaRepository.findById(id).orElseThrow(() ->
                new CozinhaNaoEncontradaException(id));
    }

    @Transactional
    public Cozinha save (Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public Cozinha save (Long id ,Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void remove (Long id) {
        try {
            cozinhaRepository.deleteById(id);
            cozinhaRepository.flush();
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(id);
        }
    }


}
