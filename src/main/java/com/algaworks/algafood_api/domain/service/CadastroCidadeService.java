package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
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
    CadastroEstadoService estadoService;

    public Cidade findById (Long id ) {
        return cidadeRepository.findById(id).orElseThrow(() ->
                    new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrado uma cidade com id de %d!" , id)
                ));
    }

    public Cidade save (Cidade cidade) {
        Estado estado = estadoService.findById(cidade.getEstado().getId());
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    public void remove (Long id) {
            cidadeRepository.findById(id).orElseThrow(() ->
                    new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrada cidade de id %d" , id)
                ));
    }

}
