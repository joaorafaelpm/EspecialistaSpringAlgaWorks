package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroEstadoService {

    EstadoRepository estadoRepository;

    public Estado findById (Long id ) {
        return estadoRepository.findById(id).orElseThrow(() ->
                new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrado um estado com id de %d!" , id)
                ));
    }

    public Estado save (Estado estado) {
        return estadoRepository.save(estado);
    }

    public void remove (Long id) {
        try {
            estadoRepository.findById(id).orElseThrow(() ->
                    new EntidadeNaoEncontradaException(
                        String.format("Estado de código %d não foi encontrado!" , id)
                ));
            estadoRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de código %d está em uso, logo, não pode ser removida!" , id)
            ) ;
        }

    }


}
