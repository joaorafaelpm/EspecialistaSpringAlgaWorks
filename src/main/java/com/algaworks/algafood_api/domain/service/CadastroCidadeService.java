package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroCidadeService {

    CidadeRepository cidadeRepository;
    EstadoRepository estadoRepository;

    public Cidade findById (Long id ) {
        return cidadeRepository.findById(id).orElseThrow(() ->
                    new CidadeNaoEncontradaException(id));
    }

    @Transactional
    public Cidade save (Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrado estado de id '%s'" , estadoId)
                ));
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }
    @Transactional
    public Cidade save(Long id, Cidade cidadeAtualizado) {
        Cidade cidadeExistente = findById(id);
        Long estadoId = cidadeAtualizado.getEstado().getId();
        Estado estado = estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrado estado de id '%s'" , estadoId)
                ));
        cidadeExistente.setEstado(estado);

        return cidadeRepository.save(cidadeExistente);
    }

    @Transactional
    public void remove (Long id) {
        cidadeRepository.deleteById(id);
        cidadeRepository.flush();
    }

}
