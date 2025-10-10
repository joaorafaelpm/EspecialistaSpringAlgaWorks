package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.model.Permissao;
import com.algaworks.algafood_api.domain.repository.GrupoRepository;
import com.algaworks.algafood_api.domain.repository.PermissaoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CadastroPermissaoService {

    private final PermissaoRepository permissaoRepository;

    public List<Permissao> findAll () {
        return permissaoRepository.findAll();
    }

    public Permissao findById (Long id ) {
        return permissaoRepository.findById(id).orElseThrow(() ->
                new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrado uma permissão com id de %d!" , id)
                ));
    }

    public Permissao save (Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

}
