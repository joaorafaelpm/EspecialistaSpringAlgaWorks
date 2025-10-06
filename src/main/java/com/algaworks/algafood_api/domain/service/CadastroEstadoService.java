package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroEstadoService {

    EstadoRepository estadoRepository;

    public Estado findById (Long id ) {
        return estadoRepository.findById(id).orElseThrow(() ->
                new EstadoNaoEncontradoException(id));
    }

    @Transactional
    public Estado save (Estado estado) {
        return estadoRepository.save(estado);
    }
    @Transactional
    public Estado save (Long id ,Estado estado) {
        return estadoRepository.save(estado);
    }

    @Transactional
    public void remove (Long id) {
        try {
            estadoRepository.deleteById(id);
            estadoRepository.flush();
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de código %d está em uso, logo, não pode ser removida!" , id)
            ) ;
        }

    }


}
