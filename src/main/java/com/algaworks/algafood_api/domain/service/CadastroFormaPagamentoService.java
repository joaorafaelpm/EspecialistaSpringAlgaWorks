package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import com.algaworks.algafood_api.domain.repository.FormaPagamentoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CadastroFormaPagamentoService {

    FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> findAll() {
        return formaPagamentoRepository.findAll();
    }

    public FormaPagamento findById (Long id) {
        return formaPagamentoRepository.findById(id).orElseThrow(() ->
                new FormaPagamentoNaoEncontradaException(id));
    }

    @Transactional
    public FormaPagamento save (FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public FormaPagamento save (Long id ,FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void remove (Long id) {
        try {
            formaPagamentoRepository.deleteById(id);
            formaPagamentoRepository.flush();
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(id);
        }
    }


}

