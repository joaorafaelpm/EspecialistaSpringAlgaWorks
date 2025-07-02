package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroCidadeService {

    CidadeRepository cidadeRepository;
    EstadoRepository estadoRepository;

    public Cidade save (Cidade cidade) {
        Estado estado = estadoRepository.getById(cidade.getEstado().getId());
        if (estado == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não foi encontrado um estado com id de %d!" , cidade.getEstado().getId())
            );
        }

        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    public void remove (Long id) {
            Cidade cidade = cidadeRepository.getById(id);
            if (cidade == null) {
                throw new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrada cidade de id %d" , id)
                );
            }



    }

}
