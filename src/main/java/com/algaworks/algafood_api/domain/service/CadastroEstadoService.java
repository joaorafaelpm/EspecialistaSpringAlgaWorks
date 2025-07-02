package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroEstadoService {

    EstadoRepository estadoRepository;

    public Estado save (Estado estado) {
        return estadoRepository.save(estado);
    }

    public void remove (Long id) {
        try {
            Estado estado = estadoRepository.getById(id);
            if (estado == null) {
                throw new EntidadeNaoEncontradaException(
                        String.format("Estado de código %d não foi encontrado!" , id)
                );
            }
            estadoRepository.remove(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de código %d está em uso, logo, não pode ser removida!" , id)
            ) ;
        }

    }


}
